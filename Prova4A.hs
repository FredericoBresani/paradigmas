module Main where
import Control.Concurrent
import Control.Concurrent.MVar

consumidor :: MVar Int -> MVar Int -> IO ()
consumidor toSend toReceive
  = do putMVar toSend 1
       v <- takeMVar toReceive
       putStrLn("Consumir leu: Mensagem "++show(v))

produtor :: (Int -> Int -> Int) -> MVar Int -> MVar Int -> MVar Int -> MVar Int -> Int -> IO ()
produtor soma contador fim toReceive toSend 0
  = do v <- takeMVar contador
       putMVar contador v
       f <- takeMVar fim
       putMVar fim (f - 1)
       putMVar toSend (v)
       putStrLn("Produtor enviou: Mensagem " ++ show(v))
produtor soma contador fim toReceive toSend n
  = do v <- takeMVar contador
       putMVar contador (soma v 1)
       putStrLn("Produtor enviou: Mensagem "++show(v))
       putMVar toSend (v)
       produtor soma contador fim toReceive toSend (n - 1)
 

waitThreads :: MVar Int -> IO ()
waitThreads fim = 
  do f <- takeMVar fim
     if (f > 0) then
         do putMVar fim f
            waitThreads fim
       else 
           return ()
       
main :: IO ()
main = do contador <- newMVar 0
          aMVar <- newEmptyMVar
          bMVar <- newEmptyMVar
          fim <- newMVar 2
          forkIO (produtor (+) contador fim aMVar bMVar 10)
          forkIO (consumidor aMVar bMVar)
          --threadDelay 1000
          return ()
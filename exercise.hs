primos :: [Int] -> [Int]
primos [] = []
primos [x] = [x]
primos (x:xs)
    | [] == [x | a <- xs, mod a x == 0] = x:primos xs
    | otherwise = primos xs


primosN :: Int -> [Int]
primosN 0 = []
primosN 1 = []
primosN n
    | [] == [n | a <- [2..n-1], mod n a == 0] = primosN (n - 1) ++ [n]
    | otherwise = primosN (n - 1)


subListas :: [a] -> [[a]]
subListas [] = [[]]
subListas (x:xs) = [[x] ++ n | n <- subListas xs] ++ [n | n <- subListas xs]

maior :: (Int, Int) -> (Int, Int)
maior (a, b)
    | a >= b = (b, a)
    | otherwise = (a, b)

fst1 :: (Int, Int) -> Int
fst1 (x, y) = x

snd1 :: (Int, Int) -> Int
snd1 (x, y) = y

menorMaior :: (Int, Int, Int) -> (Int, Int)
menorMaior (a, b, c)
    | c <= fst1 (maior (a, b)) = (c, snd1 (maior (a, b)))
    | c >= snd1 (maior (a, b)) = (fst1 (maior (a, b)), c)
    | otherwise = maior (a, b)


ordenaTripla :: (Int, Int, Int) -> (Int, Int, Int)
ordenaTripla (a, b, c)
    | c <= fM = (c, fM , sM)
    | c >= sM = (fM, sM, c)
    | otherwise = (fM, c, sM)
    where
    fM = fst1 (maior (a, b))
    sM = snd1 (maior (a, b))


type Ponto = (Float, Float)
type Reta = (Ponto, Ponto)

pontoX :: Ponto -> Float
pontoX (a, b) = a

pontoY :: Ponto -> Float
pontoY (a, b) = b

vertical :: Reta -> Bool
vertical ((x, _), (x1, _))
    | x == x1 = True
    | otherwise = False

pontoY1 :: Float -> Reta -> Float
pontoY1 x ((x1, y1), (x2, y2)) = (((x - x1)*(y2 - y1))/(x2 - x1)) + y1

maius :: Char -> Char
maius a 
    | fromEnum a <= 122 && fromEnum a >= 97 = toEnum (fromEnum a - 32)
    | otherwise = a

paraMaiuscula :: String -> String
paraMaiuscula s = [maius a | a <- s]

divisores :: Integer -> [Integer]
divisores n 
    | n <= 0 = []
    | otherwise = [a | a <- [1..n], mod n a == 0] 

isPrime :: Integer -> Bool
isPrime n
    | length (d n) /= 2 = False
    | otherwise = (d n)!!0 == 1 && (d n)!!1 == n
    where
        d x = divisores x 

menorLista :: [Int] -> Int
menorLista [a] = a
menorLista (x:xs)
    | x < menorLista xs = x
    | otherwise = menorLista xs

fib :: Int -> Int
fib 0 = 0
fib 1 = 1
fib n = fib (n - 1) + fib (n - 2)



fibTable :: Int -> String
fibTable 0 = "n fib n\n0 0"
fibTable n = fibTable (n - 1) ++ "\n" ++ show(n) ++ " " ++ show (fib n)

measure :: [a] -> Int
measure [] = -1
measure [x] = 1
measure (x:xs) = 1 + measure xs

takeFinal :: [a] -> [a]
takeFinal [] = []
takeFinal [a] = []
takefinal (a:as) = as




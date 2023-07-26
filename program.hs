import Data.Char

dobro :: Integer -> Integer
dobro x = 2*x

quadruplo :: Integer -> Integer
quadruplo x = 2*dobro(x)

poli2 :: Double -> Double -> Double -> Double -> Double
poli2 a b c x = a*x*x + b*x + c


parImpar :: Int -> String
parImpar x
        | mod x 2 == 0 = "Par"
        | otherwise = "Impar"

maxThree :: Int -> Int -> Int -> Int
maxThree x y z = max (max x y) z

maxFour :: Int -> Int -> Int -> Int -> Int
maxFour x y z w = max w (maxThree x y z)

quantosIguais :: Int -> Int -> Int -> Int
quantosIguais x y z
        | x == y && y == z = 3
        | x == y = 2
        | y == z = 2
        | otherwise = 0

ehZero :: Int -> Bool
ehZero 0 = True
ehZero _ = False

sumTo :: Integer -> Integer
sumTo 1 = 1
sumTo n = n + sumTo(n-1)

potencia :: Integer -> Integer -> Integer
potencia n 1 = n
potencia n k = n*(potencia n (k-1)) 

binomiais :: Integer -> Integer -> Integer
binomiais n 0 = 1
binomiais 0 k = maiorQue k
        where 
        maiorQue :: Integer -> Integer
        maiorQue k
                | k > 0 = 0
                | otherwise = k
binomiais n k = binomiais (n-1) k + binomiais (n-1) (k-1)

        

{-
fib(4) + fib(3)
fib(3) + fib(2) + fib(2) + fib(1)
fib(2) + fib(1) + fib(2) + fib(2) + fib(1)
-}

somdobroQuadrado :: Int -> Int -> Int
somdobroQuadrado x y = let dq a = 2*a*a
                        in dq x + dq y


fat :: Integer -> Integer
fat n
 | n == 0 = 1
 | n == 1 = 1
 | otherwise = n * fat(n-1)


ehDigito :: Char -> Bool
ehDigito a
        | fromEnum a >= 48 && fromEnum a <= 57 = True
        | otherwise = False

altaOrdem :: (f -> s) -> [f] -> [s]
altaOrdem function [] = []
altaOrdem function (b:bs) = (function b) : altaOrdem function bs

compreLista :: (f -> s) -> [f] -> [s]
compreLista function [] = []
compreLista function (x:xs) = [function a | a <- x:xs]

filtro :: (f -> Bool) -> [f] -> [f]
filtro function [] = []
filtro function (x:xs) = [y | y <- x:xs, function y]


folding :: (t -> t -> t) -> [t] -> t
folding function [a] = a
folding function (x:xs) = function x (folding function xs)


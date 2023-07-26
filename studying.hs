import Data.Char

addEspacos :: Int -> String
addEspacos n = if n <= 0 then "" else " "++addEspacos (n - 1)

paraDireita :: String -> Int -> String
paraDireita xs n = (addEspacos n)++xs

lastButOne :: [a] -> a 
lastButOne xs = last (take (length xs - 1) xs)

vendas :: Int -> Int
vendas 0 = 12
vendas 1 = 20
vendas 2 = 40
vendas 3 = 10

printHeader:: String
printHeader = "Semana Venda"

printVendas:: Int -> String
printVendas n = printHeader ++ "\n" ++ show(vendas 0)

maxi:: Int -> Int -> Int
maxi n m 
    | n >= m = n
    | otherwise = m

sumDoubleSquare :: Int -> Int -> Int
sumDoubleSquare x y = dSw x + dSw y
    where dSw w = 2 * (w * w)

sumSquares:: Int -> Int -> Int
sumSquares x y = let sqX = x * x
                     sqY = y * y
                 in sqX + sqY

maxSquare:: Int -> Int -> Int
maxSquare x y
    | sqx > sqy = sqx
    | otherwise = sqy
    where
    sqx = sq x
    sqy = sq y
    sq :: Int -> Int
    sq n = n * n

fatorial:: Integer -> Integer
fatorial x
    | x == 0 = 1 
    | x > 0 = x * fatorial(x - 1)

fib:: Int -> Int
fib n
    | n == 0 = 0
    | n == 1 = 1
    | n > 1 = fib(n - 1) + fib(n - 2)

resto:: Int -> Int -> Int
resto x y
    | x < y = x
    | x == y = 0
    | otherwise = resto (x - y) y

tailFat 0 x = x
tailFat n x = tailFat(n-1)(n*x)

fat n = tailFat n 1


--Equivalent to Object in C++
-- When defining data types using "data", we get algebrai data types, for example:
-- If we have two equal data types, but with differing names, int hasket seight, they are different
data BookInfo = Book Int String [String]
                deriving (Show) --This is for the ghci know how to print it

data MagazineInfo = Magazine Int String [String]
                    deriving (Show)
cg = Book 123 "Multiple view geometry" ["Eu", "Euzinho"]


type CustomerID = Int
type CardHolder = String
type CardNumber = String
type Address = [String]
type CashOnDelivery = Bool

data BillingInfo = CreditCard CardHolder CardNumber Address
                   | CashOnDelivery --Optional constructor
                   | Invoice CustomerID --Optional constructor
                    deriving (Show)


--Enum equivalent in C
data RgbColor = Red
              | Orange
              | Yellow
              | Green
              | Blue
              | Indigo
              | Violet
              deriving (Show) 


type Vector = (Double, Double)

-- Equivalente to union in C
data Shape = Circle Vector Double
           | Poly [Vector]
-- If creating a shape with the Circle constructor or the poly constructor,
-- this information will be available later, for consulting
-- And we would not be able to mischief them


sumList (x:xs) = x + sumList xs
sumList [] = 0


-- This allows us to look inside objects and tuples
complicated (True, a, x:xs, 5) = (a, xs)
-- call: complicated (True, 1, [1,2,3], 5)


-- Extracting values from objects
bookId (Book id title authors) = id
bookTitle (Book id title authors) = title
bookAuthors (Book id title authors) = authors
-- Run with: bookId (Book 3 "Probability Theory" ["E.T.H. Jaynes"])


addPair:: (Int, Int) -> Int
addPair (0, y) = y
addPair (x, y) = x + y

addPair2:: (Int, Int) -> Int
addPair2 p = fst p + snd p -- Seleciona o primeiro e segundo valores da tupla (fst e snd) 

shift:: ((Int, Int), Int) -> (Int, (Int, Int))
shift ((x, y), z) = (x, (y, z))




fibStep :: (Integer, Integer) -> (Integer, Integer)
fibStep (u, v) = (v, u + v)

fibPair:: Integer -> (Integer, Integer)
fibPair n
    | n == 0 = (0, 1)
    | otherwise = fibStep(fibPair(n - 1))

fastFib:: Integer -> Integer
fastFib n = fst (fibPair n)

delta:: (Float, Float, Float) -> Float
delta (a, b, c) = b^2 - 4*a*c

roots:: (Float, Float, Float) -> String
roots (a, b, c)
    | delta (a, b, c) > 0 = show((-b + sqrt(delta (a, b, c)))/(2*a))++" "++show((-b - sqrt(delta (a, b, c)))/(2*a))
    | delta (a, b, c) == 0 = show(-b/(2*a))
    | otherwise = "Nenhuma raiz"


ehPar:: Int -> Bool
ehPar n
    | mod n 2 == 0 = True
    | otherwise = False

--Maneiras de expressar listas
listasF:: Int -> [Float]
listasF 0 = [2.8..5.0]
listasF 1 = [2.8, 3.3..5]

listasS:: Int -> [Char]
listasS 0 = ['H', 'e', 'y']
listasS 1 = "Hey";

listasB:: Int -> [Bool]
listasB 0 = [ehPar n | n <- [1, 2, 3, 4, 5]]

listasI:: Int -> [Int]
listasI 0 = [2..7]
listasI 1 = [7, 5..0]
listasI 2 = [2..2]
listasI 3 = [2,7..4]
listasI 4 = [2 * n | n <- [2, 4, 7]]
listasI 5 = [2 * n | n <- [1, 2, 3, 4], ehPar n]
listasI 6 = [a + b | (a, b) <- [(1, 2), (2, 3), (3, 4), (4, 4)]]
listasI 7 = [a + b | (a, b) <- [(1, 2), (2, 3), (3, 4), (4, 4)], a == b]
listasI 8 = 1:[] --adiciona 1 ao inicio na lista
listasI 9 = [1, 2, 3, 4] ++ [7, 8]
listasI 10 = concat [[1, 2, 3], [6, 7, 8], [9]]
listasI 11 = 5:4:3:2:1:[]
-- [2,9,8..1] Erro de compilação em .., aparentemente não podemos usar mais de 2 argumentos nessas chamadas

todosPares xs = (xs == [x | x <- xs, ehPar x])
itemFromList n xs = xs!!n
splitIn n xs = splitAt n xs
-- Outras funções sobre listas: head, tail, take, drop, init, reverse, replicate

head2 :: [a] -> a
head2 (x:_) = x -- O : é o construtor da lista e x sempre é o primeiro elemento

tail2 :: [a] -> [a]
tail2 (_:xs) = xs

emptyList :: [a] -> Bool
emptyList [] = True
emptyList (_:_) = False

maiorDaLista :: [Int] -> Int
maiorDaLista [] = minBound :: Int
maiorDaLista [x] = x
maiorDaLista (x:xs)
    | x > maiorDaLista xs = x
    | otherwise = maiorDaLista xs

maiorDaLista2 :: [Int] -> Int
maiorDaLista2 (x:xs)
    | [] == [y | y <- xs, x < y] = x
    | otherwise = maiorDaLista2 xs


replicate1 0 ch = [ ]
replicate1 n ch = ch : replicate1 (n - 1) ch

zip1 :: [a] -> [b] -> [(a, b)]
zip1 (x:xs) (y:ys) = (x, y) : zip1 xs ys
zip1 _ _ = []


qsort :: [Integer] -> [Integer]
qsort [] = []
qsort (x:xs) = menorOuIgualX ++ [x] ++ maiorX
    where 
        menorOuIgualX = qsort [y | y <- xs, y <= x]
        maiorX = qsort [y | y <- xs, y > x]


printBreakLine = putStr("Alo" ++ "\n" ++ "Alo" ++ "\n")


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

composing :: Int -> Int
composing n = n + 1

{-
We could call (composing.composing) 2 - resultando em 4
or (.) composing composing 2 - também resultando em 4
-}

iter :: Int -> (t -> t) -> (t -> t)
iter 0 function = id
iter n function = (iter (n - 1) function).function

{- Call iter 2 composing 2-}


iSort :: [Int] -> [Int]
iSort [] = []
iSort [a] = [a]
iSort (x:xs) = ins x (iSort xs)

ins :: Int -> [Int] -> [Int]
ins x [] = [x]
ins x (y:ys)
    | x <= y = x:(y:ys)
    | otherwise = y:ins x ys

head1 :: [a] -> a
head1 l = case l of
            [] -> error "lista vazia"
            (x:_) -> x

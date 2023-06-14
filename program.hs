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

data BookInfo = Book Int String [String]
                deriving (Show)
data MagazineInfo = Magazine Int String [String]
                    deriving (Show)
cg = Book 123 "Multiple view geometry" ["Eu", "Euzinho"]


type CustomerID = Int
type CardHolder = String
type CardNumber = String
type Address = [String]
type CashOnDelivery = Bool

data BillingInfo = CreditCard CardHolder CardNumber Address
                   | CashOnDelivery
                   | Invoice CustomerID
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


-- Equivalente to union in C
data Shape 






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
takeFinal (a:as) = as

removeN :: Int -> [a] -> [a]
removeN _ [] = []
removeN _ [x] = []
removeN n l = take n l ++ drop (n + 1) l


primeiroInteiro :: [Int] -> Int
primeiroInteiro l
    | length l > 2 = head l + l!!1
    | length l == 2 = head l
    | otherwise = 0

produto :: [Integer] -> Integer
produto [] = 1
produto [a] = a
produto (x:xs) = x * produto xs

unique :: [Integer] -> [Integer]
unique [] = []
unique [x] = [x]
unique (x:xs)
    | [] == [x | n <- xs, x == n] = [x] ++ unique xs
    | otherwise = unique xs


crescente :: [Int] -> Bool
crescente [] = True
crescente [a] = True
crescente (x:xs) 
    | [] == [x | n <- xs, n < x] = crescente xs
    | otherwise = False

divSeg :: Float -> Float -> Maybe Float
divSeg n 0 = Nothing
divSeg n x = Just (n/x) 

data Resultado a b = Rit a | Lt b
    deriving (Show, Eq, Ord, Read)

divSeg1 :: Float -> Float -> Resultado Float String
divSeg1 n 0 = Lt "123/0"
divSeg1 n x = Rit (n/x)

funcT :: Int -> Maybe Int
funcT 0 = Nothing
funcT n = Just n

mapMaybe :: [Int] -> (Int -> Maybe Int) -> [Int]
mapMaybe l f = [n | n <- l, f n /= Nothing]

data Eitan = Lef Int | Rig Bool
    deriving (Show)

classificaLef :: [Eitan] -> [Int]
classificaLef [Rig b] = []
classificaLef [Lef a] = [a]
classificaLef ((Lef a):xs) = [a] ++ classificaLef xs
classificaLef ((Rig b):xs) = [] ++ classificaLef xs

classificaRig :: [Eitan] -> [Bool]
classificaRig [Rig b] = [b]
classificaRig [Lef a] = []
classificaRig ((Lef a):xs) = [] ++ classificaRig xs
classificaRig ((Rig b):xs) = [b] ++ classificaRig xs

classifica :: [Eitan] -> ([Bool], [Int]) 
classifica l = (classificaRig l, classificaLef l)

data Tree a = Leaf | Node a (Tree a) (Tree a)
    deriving (Show, Eq)

valRoot :: Tree a -> Maybe a
valRoot Leaf = Nothing
valRoot (Node v x y) = Just v  

tamanhoTree :: Tree Int -> Int
tamanhoTree Leaf = 0
tamanhoTree (Node v l r) = 1 + tamanhoTree l + tamanhoTree r

leftMost :: Tree a -> Maybe a
leftMost Leaf = Nothing
leftMost (Node v Leaf Leaf) = Just v
leftMost (Node v l r) = leftMost l

mapTree :: Tree Int -> Tree Int
mapTree Leaf = Leaf
mapTree (Node v l r) = Node (v + 2) (mapTree l) (mapTree r)

addLeftmost :: Int -> Tree Int -> Tree Int
addLeftmost n Leaf = Node n Leaf Leaf
addLeftmost n (Node v l r) = Node v (addLeftmost n l) r

medida :: Tree Int -> Tree Int
medida Leaf = Leaf
medida (Node v l r) = Node (tamanhoTree (Node v l r)) (medida l) (medida r)

foldTree :: Tree Int -> Int
foldTree Leaf = 0
foldTree (Node v l r) = v + foldTree l + foldTree r

findDifIndex :: [Int] -> [Int] -> Int
findDifIndex (x:xs) (y:ys)
    | x /= y = 0
    | otherwise = 1 + findDifIndex xs ys

findDifference :: [Int] -> [Int] -> Maybe [Char]
findDifference a b
    | a == b = Nothing
    | length a == length b = Just ("Valor " ++ show (a!!(findDifIndex a b)) ++ " /= " ++ "Valor " ++ show (b!!(findDifIndex a b)))
    | otherwise = Just ("Comprimento " ++ show (length a) ++ " /= " ++ "Comprimento " ++ show (length b))

data Vetor = Vetors Integer Integer Integer
    deriving (Show)


instance Eq Vetor where
    (Vetors a b c) == (Vetors x y z) = a == x && b == y && c == z
    (Vetors a b c) /= (Vetors _ _ _) = False

instance Num Vetor where
    (Vetors a b c) + (Vetors x y z) = Vetors (a + x) (b + y) (c + z)
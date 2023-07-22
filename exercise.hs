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
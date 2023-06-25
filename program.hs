{- #########################################################

        FirstScript.hs
        Simon Thompson, August 2010.

######################################################### -}

module FirstScript where

--      The value size is an integer (Integer), defined to be 
--      the sum of twelve and thirteen.

size :: Integer
size = 12+13

--      The function to square an integer.

square :: Integer -> Integer
square n = n*n

--      The function to double an integer.
        
double :: Integer -> Integer
double n = 2*n

--      An example using double, square and size.
         
example :: Integer
example = double (size - square (2+2))

doubleSquare :: Integer -> Integer
doubleSquare x = double(square x)

charToNum :: Char -> Int
charToNum c
    | fromEnum (c) > 57 || fromEnum(c) < 48 = 0
    | otherwise = fromEnum(c) - 48
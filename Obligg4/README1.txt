1. The alghorithm works by first comparing the last char in the needle with the first possible location to where it can be
(i.e with a needle lenght of 3 the program will start at hay[needle.lenght - 1]). and if it gets a match, then it moves the needle and hay pointer one to the left, and keeps
on doing this to either it has checked every char in the needle or it reaches a mismatch of chars in hay and needle. if it succeeds in findig the needle, it adds the haystack
pointer into an arraylist which marks the beginning of the word it found and increases the offset by one due to the possibility of the located word containing the start of the next word.
and when it finds a mismatch then moves the hay pointer by a fixed amount based on if char it is on is located in the needle array. and then it continues doing this until it has reached the
end of the haystack, which terminates the alghorithms and returns the arraylist containing the startindex of the words it found
2. compile with javac *.java
3. Main program is in Obligg4 java. "java Obligg4 <needle>.txt <hay>.txt" to run the program.
4. i did not make any particular assumptions in this project
5. nothing to special.
6. Everything should be working
7. I based my code on the example of moore-horspool bad shift alghorithm which we went through at the lecture.
 (i think it was 27.10)


Testing of the alghorithm:
to run the test file: java Test
1.
  input:
    N = abc, H = abcaaaabc
  Expected output:
    0 abc
    6 abc
  Output:
    0 abc
    6 abc
2.
  input:
    N = a_c, H = abcaaaabc
  Expected output:
    0 abc
    3 aac
    6 abc
  Output:
    0 abc
    3 aac
    6 abc
3.
  input:
    N = b__, H = abcaaaabc
  Expected output:
    1 bcaa
  Output:
    1 bcaa

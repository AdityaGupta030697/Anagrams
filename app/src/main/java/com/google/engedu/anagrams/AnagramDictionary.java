/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    public HashMap<Integer, ArrayList<String>> sizeToWords = new HashMap<>();
    private Random random = new Random();
    private ArrayList<String> wordlist;
    private HashSet<String> wordSet;
    private HashMap<String, ArrayList<String>> lettersToWord;
    private int wordLength = DEFAULT_WORD_LENGTH;

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line, key;
        wordlist = new ArrayList();
        wordSet = new HashSet<>();
        lettersToWord = new HashMap<>();
        while ((line = in.readLine()) != null) {
            String word = line.trim();
            //add the word to the Set
            wordSet.add(word);
            //add the word to the ArrayList
            wordlist.add(word);
            //Create new ArrayList
            ArrayList arrayList = new ArrayList();
            //key is the word in sorted order
            key = alphabeticalOrder(word);
            //check if key is already present
            if (!lettersToWord.containsKey(key)) {
                //add the word
                arrayList.add(word);
                //add the arraylist with the key
                lettersToWord.put(key, arrayList);
            } else {
                //get the arraylist using the key
                arrayList = lettersToWord.get(key);
                arrayList.add(word);
                //add the arraylist with the key
                lettersToWord.put(key, arrayList);
            }
            //check word length and store in sizeToWords
            if (sizeToWords.containsKey(word.length())) {
                sizeToWords.get(word.length()).add(word);
            } else {
                ArrayList<String> temp = new ArrayList<>();
                temp.add(word);
                sizeToWords.put(word.length(), temp);
            }
        }


    }

    public boolean isGoodWord(String word, String base) {
        //if valid, then hashSet has that word in it && the word should not be a subString of the baseWord; i.e prefix or postfix
        return wordSet.contains(word) && (!word.contains(base));


    }

    public List<String> getAnagrams(String targetWord) {

        ArrayList<String> result = new ArrayList<String>();
        //sort the target word
        String sortedWord = alphabeticalOrder(targetWord);

        //iterate through all words to find anagrams
        for (String word : wordlist) {
            String sorted = alphabeticalOrder(word);
            //if it matches to sortedTargetWord, then it's an anagram of it
            if (sorted.equals(sortedWord)) {
                //add the original word
                result.add(word);
            }
        }

        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<String> temp = new ArrayList<String>();
        for (int i = 0; i < 26; i++) {
            String new_word = word + (char) (97 + i);
            String sorted_new_word = alphabeticalOrder(new_word);
            if (lettersToWord.containsKey(sorted_new_word)) {
                temp = lettersToWord.get(sorted_new_word);
                for (String tword : temp) {
                    if (!tword.contains(word)) //redundant but required
                        result.add(tword);
                }


            }

        }
        return result;
    }

    public String pickGoodStarterWord() {

        while (true) {


            //get all words with 3/4/5 letters and pick from them only
            ArrayList<String> tempList = sizeToWords.get(wordLength);

            //generate a random number between 0 and sizeOf list obtained
            int num = random.nextInt(tempList.size());


            //pick random word from the arrayList
//            String randomWord = wordList.get(num);
            String randomWord = tempList.get(num);

            //get all the anagrams for that random word
            ArrayList<String> arrayList = (ArrayList<String>) getAnagramsWithOneMoreLetter(randomWord);

            //validate the conditions given
            if ((randomWord.length() == wordLength) && arrayList.size() > MIN_NUM_ANAGRAMS) {

                //increment the wordLength for next stage
                if (wordLength < MAX_WORD_LENGTH) wordLength++;
                return randomWord;
            }
        }


    }

    /**
     * Sorts given word in ascending order
     *
     * @param word word to be sorted
     * @return sorted word
     */

    public String alphabeticalOrder(String word) {
        char[] ch = word.toCharArray();
        Arrays.sort(ch);
        return new String(ch);

    }
}

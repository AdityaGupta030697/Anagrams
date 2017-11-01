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
    private Random random = new Random();
    private ArrayList<String> wordlist;
    private HashSet<String> wordSet;
    private HashMap<String, ArrayList<String>> lettersToWord;

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line, key;
        wordlist = new ArrayList();
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

        }
    }

    public boolean isGoodWord(String word, String base) {
        return true;
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
        return result;
    }

    public String pickGoodStarterWord() {
        return "ate";
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

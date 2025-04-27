package asia.fourtitude.interviewq.jumble.core;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Chars;
import org.springframework.boot.context.properties.bind.DefaultValue;

public class JumbleEngine {

    public List<String> getCollection(){

        List<String> data = new ArrayList<String>();
         InputStream is = this.getClass().getClassLoader().getResourceAsStream("words.txt");

         try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while(br.ready()){
               // System.out.println(br.readLine());
               data.add(br.readLine());
            }
         } catch (Exception e) {
            e.printStackTrace();
         }

         return data;

    }

    /**
     * From the input `word`, produces/generates a copy which has the same
     * letters, but in different ordering.
     *
     * Example: from "elephant" to "aeehlnpt".
     *
     * Evaluation/Grading:
     * a) pass unit test: JumbleEngineTest#scramble()
     * b) scrambled letters/output must not be the same as input
     *
     * @param word  The input word to scramble the letters.
     * @return  The scrambled output/letters.
     */
    public String scramble(String word) {
        /*
         * Refer to the method's Javadoc (above) and implement accordingly.
         * Must pass the corresponding unit tests.
         */
    
        int wordlength = word.length();
        List<Character> wrd = word.chars().mapToObj(a -> (char) a).collect(Collectors.toList());

         Random r = new Random();
         
         StringBuilder sb = new StringBuilder();

         for(int i = 0 ; i < wordlength ; i++){
            int randomInt = r.nextInt(100);
            int position = randomInt%wrd.size();
            sb.append(wrd.get(position));
            wrd.remove(position);
         }


         return sb.toString();

    }

    /**
     * Retrieves the palindrome words from the internal
     * word list/dictionary ("src/main/resources/words.txt").
     *
     * Word of single letter is not considered as valid palindrome word.
     *
     * Examples: "eye", "deed", "level".
     *
     * Evaluation/Grading:
     * a) able to access/use resource from classpath
     * b) using inbuilt Collections
     * c) using "try-with-resources" functionality/statement
     * d) pass unit test: JumbleEngineTest#palindrome()
     *
     * @return  The list of palindrome words found in system/engine.
     * @see https://www.google.com/search?q=palindrome+meaning
     */
    public Collection<String> retrievePalindromeWords() {
        /*
         * Refer to the method's Javadoc (above) and implement accordingly.
         * Must pass the corresponding unit tests.
         */
        
        List<String> data = getCollection();

         List<String> newList = data.stream()
         .map(c->c.toLowerCase())
         .filter(c-> c.equals(new StringBuilder().append(c).reverse().toString()) )
         .collect(Collectors.toList());

         //System.out.println(newList);

         //System.out.println(newList.size());


         return newList;

    }

    /**
     * Picks one word randomly from internal word list.
     *
     * Evaluation/Grading:
     * a) pass unit test: JumbleEngineTest#randomWord()
     * b) provide a good enough implementation, if not able to provide a fast lookup
     * c) bonus points, if able to implement a fast lookup/scheme
     *
     * @param length  The word picked, must of length.
     *                When length is null, then return random word of any length.
     * @return  One of the word (randomly) from word list.
     *          Or null if none matching.
     */
    public String pickOneRandomWord(Integer length) {
        /*
         * Refer to the method's Javadoc (above) and implement accordingly.
         * Must pass the corresponding unit tests.
         */

        List<String> data = new ArrayList<String>();
         InputStream is = this.getClass().getClassLoader().getResourceAsStream("words.txt");
        int maxLength = 0;

         try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while(br.ready()){
               // System.out.println(br.readLine());
               String readdata = br.readLine();
               data.add(readdata);
               if(readdata.length() > maxLength){
                maxLength = readdata.length() ;
               }
               
            }
         } catch (Exception e) {
            e.printStackTrace();
         }

         String output  = null;
         if(length == null){
             output = data.stream().filter(c -> c.length() == new Random().nextInt(10-2+1)+2).collect(Collectors.toList()).get(0);
         }else if(length > maxLength){
            //
         }
         else{
            output = data.stream().filter(c -> c.length() == length).collect(Collectors.toList()).get(0);
         }
         
         System.out.println(output+"length="+length);
         return output;
    }

    /**
     * Checks if the `word` exists in internal word list.
     * Matching is case insensitive.
     *
     * Evaluation/Grading:
     * a) pass related unit tests in "JumbleEngineTest"
     * b) provide a good enough implementation, if not able to provide a fast lookup
     * c) bonus points, if able to implement a fast lookup/scheme
     *
     * @param word  The input word to check.
     * @return  true if `word` exists in internal word list.
     */
    public boolean exists(String word) {
        /*
         * Refer to the method's Javadoc (above) and implement accordingly.
         * Must pass the corresponding unit tests.
         */
        //throw new UnsupportedOperationException("to be implemented");

        List<String> data = getCollection();
        boolean exist = data.stream().map(c->c.toLowerCase()).anyMatch(c->c.equals(word.toLowerCase()));
        return exist;
    }

    /**
     * Finds all the words from internal word list which begins with the
     * input `prefix`.
     * Matching is case insensitive.
     *
     * Invalid `prefix` (null, empty string, blank string, non letter) will
     * return empty list.
     *
     * Evaluation/Grading:
     * a) pass related unit tests in "JumbleEngineTest"
     * b) provide a good enough implementation, if not able to provide a fast lookup
     * c) bonus points, if able to implement a fast lookup/scheme
     *
     * @param prefix  The prefix to match.
     * @return  The list of words matching the prefix.
     */
    public Collection<String> wordsMatchingPrefix(String prefix) {
        /*
         * Refer to the method's Javadoc (above) and implement accordingly.
         * Must pass the corresponding unit tests.
         */
        //throw new UnsupportedOperationException("to be implemented");

        List<String> data  = getCollection();
        
        if(prefix == null){
            return new ArrayList<>();
        }

        if(Pattern.matches("[a-zA-Z]+", prefix)){
            return data.stream()
            .map(c->c.toLowerCase())
            .filter(c->c.startsWith(prefix)).collect(Collectors.toList());
        }else{
            return new ArrayList<>();
        }
    }

    /**
     * Finds all the words from internal word list that is matching
     * the searching criteria.
     *
     * `startChar` and `endChar` must be 'a' to 'z' only. And case insensitive.
     * `length`, if have value, must be positive integer (>= 1).
     *
     * Words are filtered using `startChar` and `endChar` first.
     * Then apply `length` on the result, to produce the final output.
     *
     * Must have at least one valid value out of 3 inputs
     * (`startChar`, `endChar`, `length`) to proceed with searching.
     * Otherwise, return empty list.
     *
     * Evaluation/Grading:
     * a) pass related unit tests in "JumbleEngineTest"
     * b) provide a good enough implementation, if not able to provide a fast lookup
     * c) bonus points, if able to implement a fast lookup/scheme
     *
     * @param startChar  The first character of the word to search for.
     * @param endChar    The last character of the word to match with.
     * @param length     The length of the word to match.
     * @return  The list of words matching the searching criteria.
     */
    public Collection<String> searchWords(Character startChar, Character endChar, Integer length) {
        /*
         * Refer to the method's Javadoc (above) and implement accordingly.
         * Must pass the corresponding unit tests.
         */
        //throw new UnsupportedOperationException("to be implemented");
        List<String>data = getCollection();
        List<String> returnData = new ArrayList<>();

         String start  = String.valueOf(startChar);
         String end  = String.valueOf(endChar);

        if (startChar == null && endChar == null && (length == null || length <= 0)){
            
        }else if(startChar != null && endChar == null && (length == null || length <= 0)){

            returnData =  data.stream()
            .map(c->c.toLowerCase())
            .filter(c-> c.startsWith(start.toLowerCase()))
            .collect(Collectors.toList());

        }else if(startChar == null && endChar != null && (length == null || length <= 0)){

            returnData =  data.stream()
            .map(c->c.toLowerCase())
            .filter(c-> c.endsWith(end.toLowerCase()))
            .collect(Collectors.toList());

        }else if(startChar == null && endChar == null && (length != null || length > 0)){

            returnData =  data.stream()
            .filter(c-> c.length() == length)
            .collect(Collectors.toList());

        }else if(startChar != null && endChar != null && (length == null || length <= 0)){
            returnData =  data.stream()
            .map(c->c.toLowerCase())
            .filter(c-> c.startsWith(start.toLowerCase()))
            .filter(c-> c.endsWith(end.toLowerCase()))
            .collect(Collectors.toList());

        }else if(startChar != null && endChar != null && (length != null && length > 0)){
            returnData =  data.stream()
            .map(c->c.toLowerCase())
            .filter(c-> c.startsWith(start.toLowerCase()))
            .filter(c-> c.endsWith(end.toLowerCase()))
            .filter(c-> c.length() == length)
            .collect(Collectors.toList());

        }else if(startChar != null && endChar == null && (length != null && length > 0)){
            returnData =  data.stream()
            .map(c->c.toLowerCase())
            .filter(c-> c.startsWith(start.toLowerCase()))
            .filter(c-> c.length() == length)
            .collect(Collectors.toList());

        }else if(startChar == null && endChar != null && (length != null && length > 0)){
            returnData =  data.stream()
            .map(c->c.toLowerCase())
            .filter(c-> c.endsWith(end.toLowerCase()))
            .filter(c-> c.length() == length)
            .collect(Collectors.toList());

        }

        return returnData;
    }

    /**
     * Generates all possible combinations of smaller/sub words using the
     * letters from input word.
     *
     * The `minLength` set the minimum length of sub word that is considered
     * as acceptable word.
     *
     * If length of input `word` is less than `minLength`, then return empty list.
     *
     * The sub words must exist in internal word list.
     *
     * Example: From "yellow" and `minLength` = 3, the output sub words:
     *     low, lowly, lye, ole, owe, owl, well, welly, woe, yell, yeow, yew, yowl
     *
     * Evaluation/Grading:
     * a) pass related unit tests in "JumbleEngineTest"
     * b) provide a good enough implementation, if not able to provide a fast lookup
     * c) bonus points, if able to implement a fast lookup/scheme
     *
     * @param word       The input word to use as base/seed.
     * @param minLength  The minimum length (inclusive) of sub words.
     *                   Expects positive integer.
     *                   Default is 3.
     * @return  The list of sub words constructed from input `word`.
     */
    public Collection<String> generateSubWords(String word,Integer minLength) {
        /*
         * Refer to the method's Javadoc (above) and implement accordingly.
         * Must pass the corresponding unit tests.
         */
       // throw new UnsupportedOperationException("to be implemented");

       List<String> data =  getCollection();

       List<String> empty = new ArrayList<>();

       if((word == null || word.isEmpty() || !Pattern.matches("[a-zA-Z]+", word)) && minLength == null){
        return empty;
       }

       if(minLength == null){
        return empty;
       }

       Set<Character> word1 = new HashSet<>();
       for(Character a : word.toCharArray()){
           word1.add(a);
       }

       List<String> test  = data.stream()
                .map(c->c.toLowerCase())
                .filter(c-> c.length() > minLength)
                .collect(Collectors.toList());

        List<String> toReturn = new ArrayList<>();

        for(String testA : test){
            if(compareBase(word1, testA)){
                toReturn.add(testA);
            }
        }

        System.out.println(toReturn);

       return toReturn;


    }

    public boolean compareBase(Set<Character> input,String running){

        //System.out.println(running);

        Set<Character> runningHash = new HashSet<>();
        for(Character a : running.toCharArray()){
            runningHash.add(a);
        }

        int count = 0;
        for(Character a : runningHash){
            if(input.contains(a)){
                count++;
            }
        }

        // System.out.println(count);

        if(count == runningHash.size()){
            System.out.println(running);
            return true;
        }else{
            return false;
        }

        
    }

    /**
     * Creates a game state with word to guess, scrambled letters, and
     * possible combinations of words.
     *
     * Word is of length 6 characters.
     * The minimum length of sub words is of length 3 characters.
     *
     * @param length     The length of selected word.
     *                   Expects >= 3.
     * @param minLength  The minimum length (inclusive) of sub words.
     *                   Expects positive integer.
     *                   Default is 3.
     * @return  The game state.
     */
    public GameState createGameState(Integer length, Integer minLength) {
        Objects.requireNonNull(length, "length must not be null");
        if (minLength == null) {
            minLength = 3;
        } else if (minLength <= 0) {
            throw new IllegalArgumentException("Invalid minLength=[" + minLength + "], expect positive integer");
        }
        if (length < 3) {
            throw new IllegalArgumentException("Invalid length=[" + length + "], expect greater than or equals 3");
        }
        if (minLength > length) {
            throw new IllegalArgumentException("Expect minLength=[" + minLength + "] greater than length=[" + length + "]");
        }
        String original = this.pickOneRandomWord(length);
        if (original == null) {
            throw new IllegalArgumentException("Cannot find valid word to create game state");
        }
        String scramble = this.scramble(original);
        Map<String, Boolean> subWords = new TreeMap<>();
        for (String subWord : this.generateSubWords(original, minLength)) {
            subWords.put(subWord, Boolean.FALSE);
        }
        return new GameState(original, scramble, subWords);
    }

}

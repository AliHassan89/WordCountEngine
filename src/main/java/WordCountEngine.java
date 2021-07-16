/*

Word Count Engine
Implement a document scanning function wordCountEngine, which receives a string document and returns a list of all
unique words in it and their number of occurrences, sorted by the number of occurrences in a descending order. If two
or more words have the same count, they should be sorted according to their order in the original sentence. Assume that
all letters are in english alphabet. You function should be case-insensitive, so for instance, the words “Perfect” and
“perfect” should be considered the same word.



The engine should strip out punctuation (even in the middle of a word) and use whitespaces to separate words.

Analyze the time and space complexities of your solution. Try to optimize for time while keeping a polynomial space
complexity.

Examples:

input:  document = "Practice makes perfect. you'll only get Perfect by practice. just practice!"

output: [ ["practice", "3"], ["perfect", "2"],
          ["makes", "1"], ["youll", "1"], ["only", "1"],
          ["get", "1"], ["by", "1"], ["just", "1"] ]


# Solution:
1. Normalize the string, by making all words lowercase and removing any special characters
2. We need to retain the order in which the words are appearing in the actual string in case if two words have same
frequency then they should appear according to their order in actual string.
3. In order to achieve this use LinkedHashMap where the key will be the word and value will be the frequency of the word.
Considering this example:
document = "Practice makes perfect. you'll only get Perfect by practice. just practice!"

At this point our map will look like this:
map(practice, 3)
map(makes, 1)
map(perfect, 2)
map(youll, 1)
map(only, 1)
map(get, 1)
map(by, 1)
map(just, 1)

We also keep track of the maxFrequency which in this case will be 3.

4. Initialize a list of string array with maxFrequency+1.
5. Iterate over the LinkedHashMap and place the words in buckets. The bucket index is the frequency of word. At this
point the bucket array will look like this.

bucket[1] = {makes, youll, only, get, by, just}
bucket[2] = {perfect}
bucket[3] = {practice}

6. Once the bucket is filled we just need to iterate over the bucket starting from the last index of the bucket and
keep adding them into resultant array.

 */

package main.java;

import java.util.*;

public class WordCountEngine {

    static String[][] wordCountEngine(String document) {
        List<String>[] bucket;
        Map<String, Integer> map = new LinkedHashMap<>();

        String[] docArr = normalize(document);

        int maxFrequence = Integer.MIN_VALUE;

        for(int i=0; i<docArr.length; i++) {
            int freq = 1;
            if (map.get(docArr[i]) != null)
                freq = map.get(docArr[i]) + 1;

            maxFrequence = Math.max(maxFrequence, freq);

            map.put(docArr[i], freq);
        }

        bucket = new ArrayList[maxFrequence+1];

        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Integer> entry = (Map.Entry)it.next();
            int freq = entry.getValue();
            List<String> list = bucket[freq];
            if (list == null)
                list = new ArrayList<>();
            list.add(entry.getKey());
            bucket[freq] = list;
        }

        String[][] result = new String[map.size()][2];
        int index = 0;
        for (Integer i = maxFrequence; i>0; i--) {
            if (bucket[i] == null)
                continue;

            List<String> list = bucket[i];

            for (String str : list) {
                result[index][0] = str;
                result[index][1] = i.toString();
                ++index;
            }
        }

        return result;
    }

    private static String[] normalize(String doc) {
        doc = doc.toLowerCase();
        List<String> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<doc.length(); i++){
            if (Character.isLetter(doc.charAt(i)))
                sb.append(doc.charAt(i));
            else if (doc.charAt(i) == ' ' && sb.length() > 0) {
                list.add(sb.toString());
                sb = new StringBuilder();
            }
        }

        if (sb.length() > 0)
            list.add(sb.toString());

        String[] arr = new String[list.size()];
        return list.toArray(arr);
    }

}

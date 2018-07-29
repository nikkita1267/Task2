import java.util.*;

final class Word {
    private static final Map<String, List<String>> PRONOUNS = new HashMap<>();

    static {
        PRONOUNS.put("я", Arrays.asList("я", "меня", "мне", "мной"));
        PRONOUNS.put("мы", Arrays.asList("мы", "нас", "нам", "нами"));
        PRONOUNS.put("ты", Arrays.asList("ты", "тебя", "тебе", "тобой"));
        PRONOUNS.put("вы", Arrays.asList("вы", "вас", "вам", "вами"));
        PRONOUNS.put("он", Arrays.asList("он", "его", "ему"));
        PRONOUNS.put("она", Arrays.asList("она", "её", "ей", "ней"));
        PRONOUNS.put("они", Arrays.asList("они", "их", "них"));
        PRONOUNS.put("им", Arrays.asList("им", "ним"));
        PRONOUNS.put("все", Arrays.asList("все", "всех", "всем"));
        PRONOUNS.put("всё", Arrays.asList("всё", "всей", "всю"));
        PRONOUNS.put("своё", Arrays.asList("своей", "своим", "своими"));
    }

    private Word() {
    }

    static boolean isOneWordProbablyEqualToAnother(String word1, String word2) {
        if ((PRONOUNS.containsKey(word1) && !PRONOUNS.get(word1).contains(word2)) ||
                (PRONOUNS.containsKey(word2) && !PRONOUNS.get(word2).contains(word1)) ||
                (containValueInMap(word1) && !containValueInMap(word2) ||
                        (containValueInMap(word2) && !containValueInMap(word1))))
            return false;

        if ((PRONOUNS.get(word1) != null && PRONOUNS.get(word1).contains(word2)) || (PRONOUNS.get(word2) != null && PRONOUNS.get(word2).contains(word1)))
            return true;
        else {
            if (word1.length() <= 5 || word2.length() <= 5)
                return word1.equals(word2);
            else {
                boolean isWordsProbablyEqual = word1.substring(0, word1.length() - 2).equals(word2.substring(0, word2.length() - 2));
                isWordsProbablyEqual = isWordsProbablyEqual || word1.substring(0, word1.length() - 1).equals(word2.substring(0, word2.length() - 1));
                isWordsProbablyEqual = isWordsProbablyEqual || word1.substring(0, word1.length() - 3).equals(word2.substring(0, word2.length() - 3));
                return isWordsProbablyEqual || (word1.substring(0, word1.length() - 3).equals(word2.substring(0, word2.length() - 3)) && word1.length() > 5 && word2.length() > 5);
            }
        }
    }

    private static boolean containValueInMap(String word1) {

        for (Map.Entry<String, List<String>> pairs : PRONOUNS.entrySet()) {
            for (String w : pairs.getValue()) {
                if (w.equals(word1))
                    return true;
            }
        }

        for (String s : PRONOUNS.keySet()) {
            if (s.equals(word1)) {
                return true;
            }
        }

        return false;
    }
}

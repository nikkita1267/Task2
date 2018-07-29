import java.io.*;
import java.util.*;

public class Solution {
    private static Map<String, Integer> statistic = new TreeMap<>();
    private static Map<String, List<String>> words = new HashMap<>();
    private static Set<String> numbers = new HashSet<>();
    private static final List<Character> vowels = new ArrayList<>(); //"уеаояиюёэaeiou";
    static {
        numbers.add("000");
        numbers.add("i");
        numbers.add("ii");
        numbers.add("iii");
        numbers.add("iv");
        numbers.add("v");
        numbers.add("vi");
        numbers.add("vii");
        numbers.add("viii");
        numbers.add("ix");
        numbers.add("x");
        numbers.add("xi");
        for (int i = 0; i <= 1000; i++) {
            numbers.add("" + i);
        }
        vowels.add('a');
        vowels.add('e');
        vowels.add('i');
        vowels.add('o');
        vowels.add('u');
        vowels.add('у');
        vowels.add('е');
        vowels.add('а');
        vowels.add('о');
        vowels.add('я');
        vowels.add('и');
        vowels.add('ю');
        vowels.add('ё');
        vowels.add('э');
    }

    public static void main(String[] args) throws Exception
    {
        System.out.println("Введите путь к файлу для считывания:");
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader reader = new BufferedReader(new FileReader(console.readLine()));

        StringBuilder text = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            text.append(line);
        }
        reader.close();

        String[] symbols = text.toString().split("[\\s,.:\\-;)(]");


        for (String s : symbols) {
            s = s.replaceAll("[«\"()]", "");
            s = s.toLowerCase();
            if (s.equals("")) continue;
            if (numbers.contains(s)) continue;
            if (statistic.get(s) == null) {
                putToMapOrIncrement(s);
            } else {
                statistic.computeIfPresent(s, (str, i) -> i + 1);
            }
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));

        writer.write("Приблизительное количество различных слов: " + statistic.size() + "\n\r");

        int counterOfNumbers = 0;
        int counterOfDigits = 0;
        for (String s : symbols) {
            try {
                Double.parseDouble(s);
                counterOfNumbers++;
                counterOfDigits += s.length();
                if (s.contains(".") || s.contains(",")) {
                    counterOfDigits--;
                }

            } catch (NumberFormatException ignored) {
            }
        }
        writer.write("Количество чисел: " + counterOfNumbers + "\n\r");
        writer.write("Количество цифр: " + counterOfDigits + "\n\r");

        int counterOfVowels = 0;
        for (String s : symbols) {
            for (char c : s.toCharArray()) {
                if (vowels.contains(c)) {
                    counterOfVowels++;
                }
            }
        }
        writer.write("Количество гласных в тексте: " + counterOfVowels + "\n\r");
        writer.close();
        System.out.println("Статистика заполнена (файл output.txt). Различные слова будут записаны в отдельный файл. Вы согласны? (Да/Нет)");
        Boolean answer = null;
        do {
            String answerS = console.readLine();
            if (answerS.toLowerCase().equals("да")) {
                answer = true;
            } else if (answerS.toLowerCase().equals("нет")) {
                answer = false;
            } else {
                System.out.println("Введите Да или Нет!");
            }
        } while (answer == null);

        if (answer) {
            BufferedWriter writer2 = new BufferedWriter(new FileWriter("output2.txt"));

            Set<Map.Entry<String, Integer>> counts = statistic.entrySet();
            counts.stream().sorted((o1, o2) -> Integer.compare(o2.getValue(), o1.getValue())).forEach(entry -> {
                write(writer2, entry.getKey(), entry.getValue());
            });

            writer2.close();
            System.out.println("Различные слова записаны в файл output2.txt");
        }
        console.close();
    }

    private static void write(BufferedWriter writer, String key, Integer value) {
        try {
            writer.write(key + "-" + value);
            writer.write("(");
            words.entrySet().stream().filter(pair1 -> pair1.getKey().equals(key))
                    .forEach(pair1 -> pair1.getValue().forEach(str -> {
                        try {
                            writer.write(str + " ");
                        } catch (IOException e) {
                            System.err.println("Error");
                            e.printStackTrace();
                        }
                    }));
            writer.write(")");
            writer.newLine();
        } catch (IOException ignored) {
        }
    }

    private static void putToMapOrIncrement(String s) {
        for (String s1 : statistic.keySet()) {
            if (Word.isOneWordProbablyEqualToAnother(s, s1)) {
                statistic.compute(s1, (str, i) -> i + 1);
                words.compute(s1, (str, list) -> {
                    list.add(s);
                    return new ArrayList<>(list);
                });
                return;
            }
        }
        statistic.put(s, 1);
        words.put(s, new ArrayList<>());
    }
}

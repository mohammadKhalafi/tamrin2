import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class tamrin {

    public static void makeDoc(String command, String value){

        Pattern pattern = Pattern.compile("^ *ADD DOC (.+) *$");
        Matcher matcher = pattern.matcher(command);
        matcher.find();
        int index;
        if(docsAndValues[0].contains(matcher.group(1))){
            index = docsAndValues[0].indexOf(matcher.group(1));
            docsAndValues[0].remove(index);
            docsAndValues[1].remove(index);
        }
        docsAndValues[0].add(matcher.group(1));
        value = handlePicture(value);
        value = handleLink(value);
        value = handleBold(value);
        value = handleNoise(value);
        docsAndValues[1].add(value + "");
    }

    public static String handleBold (String value){
        Pattern boldWordPattern = Pattern.compile(" (\\*+)([\\w]+)(\\*+) ");
        Matcher boldWordsMatcher = boldWordPattern.matcher(" " + value + " ");
        StringBuilder tempValue = new StringBuilder(value);

        while (boldWordsMatcher.find()){

            if(boldWordsMatcher.group(1).compareTo(boldWordsMatcher.group(3)) == 0 && boldWordsMatcher.group(1).length() % 2 == 0) {
                tempValue.replace(boldWordsMatcher.start(), boldWordsMatcher.end() - 2, boldWordsMatcher.group(2));
            }
            else{
                tempValue.replace(boldWordsMatcher.start(), boldWordsMatcher.end() - 2, "");
            }

            boldWordsMatcher = boldWordPattern.matcher(" " + tempValue.toString() + " ");
        }

        return tempValue.toString();
    }

    public static String handleLink(String value){
        Pattern pattern = Pattern.compile("\\[(.+?)\\]\\(.+?\\)");
        Matcher matcher = pattern.matcher(value);
        StringBuilder tempValue = new StringBuilder(value);
        while(matcher.find()){
            tempValue.replace(matcher.start(), matcher.end(), matcher.group(1));
            matcher = pattern.matcher(tempValue.toString());
        }
        return tempValue.toString();
    }

    public static String handlePicture(String value){
        Pattern pattern = Pattern.compile("!\\[([^\\]]+)\\]\\([^\\)]+\\)");
        Matcher matcher;
        StringBuilder tempValue = new StringBuilder(value);
        while(true){
            matcher = pattern.matcher(tempValue.toString());
            if(!matcher.find())  break;
            tempValue.replace(matcher.start(), matcher.end(), matcher.group(1));
        }
        return tempValue.toString();
    }

    public static String handleNoise(String value){
        Pattern pattern = Pattern.compile("([^ ]+)");
        Matcher matcher = pattern.matcher(value);
        StringBuilder tempValue = new StringBuilder(value);

        while(matcher.find()){
            if (!matcher.group(1).matches("[A-Za-z0-9]+")){
                tempValue.replace(matcher.start(), matcher.end(), "");
                matcher = pattern.matcher(tempValue.toString());
            }
        }
        return tempValue.toString();
    }

    public static void printDoc(String command){
        int index = getIndex("^ *PRINT ([^ ]+) *$", command);
        if(index == -1) { return; }
        System.out.println(docsAndValues[1].get(index));
    }

    public static void removeDoc(String command){
        int index = getIndex("^ *RMV DOC ([^ ]+) *$", command);
        if(index == -1) { return; }
        docsAndValues[0].remove(index);
        docsAndValues[1].remove(index);
    }

    public static void replaceOne(String command){
        int index = getIndex("^ *RPLC ([^ ]+) (?:[\\w]+,)*[\\w]+ [\\w]+ *$", command);
        if(index == -1) { return; }
        Matcher replacedWordMatcher = getMatcher("^ *RPLC [^ ]+ (?:[\\w]+,)*[\\w]+ ([\\w]+) *$", command);
        replacedWordMatcher.find();
        String replacedWord = replacedWordMatcher.group(1);
        Matcher oldWordsMatcher = getMatcher("^ *RPLC [^ ]+ ((?:[\\w]+,)*[\\w]+) [\\w]+ *$", command);
        oldWordsMatcher.find();
        String []oldWords = oldWordsMatcher.group(1).split(",", -1);
        String tempValue = docsAndValues[1].get(index);
        StringBuilder tempValue1 = new StringBuilder(tempValue);
        int oldWordIndex;
        for(String oldWord : oldWords){
            oldWordIndex = (" " + tempValue + " ").lastIndexOf(" " + oldWord + " ");
            if(oldWordIndex == -1){ continue; }
            tempValue1.replace(oldWordIndex, oldWordIndex + oldWord.length(), replacedWord);
            tempValue = tempValue1.toString();
        }
        docsAndValues[1].set(index, tempValue);
    }

    public static void replaceAll(String command){

        Matcher replacedWordMatcher = getMatcher("^ *RPLC -ALL (?:[\\w]+,)*[\\w]+ ([\\w]+) *$", command);
        replacedWordMatcher.find();
        String replacedWord = replacedWordMatcher.group(1);
        Matcher oldWordsMatcher = getMatcher("^ *RPLC -ALL ((?:[\\w]+,)*[\\w]+) [\\w]+ *$", command);
        oldWordsMatcher.find();
        String []oldWords = oldWordsMatcher.group(1).split(",", -1);

        int index;
        for(int i = 0; i < docsAndValues[0].size(); i++){
            index = i;
            String tempValue = docsAndValues[1].get(index);
            StringBuilder tempValue1 = new StringBuilder(tempValue);
            int oldWordIndex;
            for(String oldWord : oldWords){
                oldWordIndex = (" " + tempValue + " ").lastIndexOf(" " + oldWord + " ");
                if(oldWordIndex == -1){ continue; }
                tempValue1.replace(oldWordIndex, oldWordIndex + oldWord.length(), replacedWord);
                tempValue = tempValue1.toString();
            }
            docsAndValues[1].set(index, tempValue);
        }
    }

    public static void removeOne(String command){
        int index = getIndex("^ *RMV WORD ([^ ]+) [\\w]+ *$", command);
        if(index == -1) { return; }
        Matcher removingWordMatcher = getMatcher("^ *RMV WORD [^ ]+ ([\\w]+) *$", command);
        removingWordMatcher.find();
        String removingWord = removingWordMatcher.group(1);
        removeWord(index, removingWord);
    }

    public static void removeAll(String command){
        Matcher removingWordMatcher = getMatcher("^ *RMV WORD -ALL ([\\w]+) *$", command);
        removingWordMatcher.find();
        String removingWord = removingWordMatcher.group(1);
        for(int i = 0; i < docsAndValues[0].size(); i++)
            removeWord(i, removingWord);
    }

    public static void removeWord(int index, String removingWord){

        String tempValue = docsAndValues[1].get(index);
        StringBuilder tempValue1 = new StringBuilder(tempValue);

        int removingWordIndex = (" " + tempValue + " ").indexOf(" " + removingWord + " ");

        while (removingWordIndex != -1){
            tempValue1.replace(removingWordIndex, removingWordIndex + removingWord.length(), "");
            tempValue = tempValue1.toString();
            removingWordIndex = (" " + tempValue + " ").indexOf(" " + removingWord + " ");
        }
        docsAndValues[1].set(index, tempValue);
    }


    public static void  addOne(String command){
        int index = getIndex("^ *ADD WORD ([^ ]+) [\\w]+ *$", command);
        if(index == -1) { return; }

        Matcher addingWordMatcher = getMatcher("^ *ADD WORD [^ ]+ ([\\w]+) *$", command);
        addingWordMatcher.find();
        String addingWord = addingWordMatcher.group(1);

        addWord(index, addingWord);
    }

    public static void addAll(String command){

        Matcher addingWordMatcher = getMatcher("^ *ADD WORD -ALL ([\\w]+) *$", command);
        addingWordMatcher.find();
        String addingWord = addingWordMatcher.group(1);

        for(int i = 0; i < docsAndValues[0].size(); i++){
            addWord(i, addingWord);
        }
    }

    public static void addWord(int index, String addingWord){
        StringBuilder tempValue = new StringBuilder(docsAndValues[1].get(index));
        tempValue.append(addingWord);
        docsAndValues[1].set(index, tempValue.toString());
    }


    public static void findRep(String command) {
        int index = getIndex("^ *FIND REP ([^ ]+) [\\w]+ *$", command);
        if(index == -1) { return; }

        Matcher repeatedStringMatcher = getMatcher("^ *FIND REP [^ ]+ ([\\w]+) *$", command);
        repeatedStringMatcher.find();
        String repeatedStr = repeatedStringMatcher.group(1);
        int coIndex = repeatedStr.length() - 1;

        Matcher stringMatcher = getMatcher(repeatedStr, docsAndValues[1].get(index));
        int numRep = 0;
        int tempIndex;
        if(stringMatcher.find()) {
            numRep++;
            tempIndex = stringMatcher.end() - coIndex;
            while (stringMatcher.find(tempIndex)) {
                tempIndex = stringMatcher.end() - coIndex;
                numRep++;
            }
        }
        System.out.println(repeatedStr + " is repeated " + numRep +" times in " + docsAndValues[0].get(index));
    }

    private static void findMirror(String command) {
        int index = getIndex("^ *FIND MIRROR ([^ ]+) [\\w] *$", command);
        if(index == -1) { return; }

        Matcher charMatcher = getMatcher("^ *FIND MIRROR [^ ]+ ([\\w]) *$", command);
        charMatcher.find();
        String middleChar = charMatcher.group(1);
        Matcher mirrorWordMatcher = getMatcher(" ([\\d]+)" + middleChar + "([\\d]+) ", " " + docsAndValues[1].get(index) + " ");
        int numMirrorWords = 0;
        String firstNum;
        String secondNum;
        if(mirrorWordMatcher.find()){
            numMirrorWords++;
            int tempIndex = mirrorWordMatcher.end() - 1;
            while(mirrorWordMatcher.find(tempIndex)){
                firstNum = mirrorWordMatcher.group(1);
                secondNum = mirrorWordMatcher.group(2);
                if(firstNum.compareTo(secondNum) == 0){
                    numMirrorWords++;
                }
                tempIndex = mirrorWordMatcher.end() - 1;
            }
        }

        System.out.println(numMirrorWords + " mirror words!");
    }


    private static void findAlphabet(String command) {
        int index = getIndex("^ *FIND ALPHABET WORDS ([^ ]+) *$", command);
        if(index == -1) { return; }

        Matcher alphabetMatcher = getMatcher(" [a-zA-Z]+ ", " " + docsAndValues[1].get(index) + " ");
        int numAlphabet = 0;
        if(alphabetMatcher.find()){
            numAlphabet++;
            int tempIndex = alphabetMatcher.end() - 1;
            while (alphabetMatcher.find(tempIndex)){
                numAlphabet++;
                tempIndex = alphabetMatcher.end() - 1;
            }
        }

        System.out.println(numAlphabet + " alphabetical words!");
    }


    private static void addGcd(String command) {
        int index = getIndex("^ *GCD ([^ ]+) *$", command);
        if(index == -1) { return; }

        StringBuilder tempValue = new StringBuilder(docsAndValues[1].get(index));
        Matcher numMatcher = getMatcher("[\\d]+", tempValue.toString());
        int tempNum;
        ArrayList<Integer> numbers = new ArrayList<>();
        while (numMatcher.find()){
            tempNum = Integer.parseInt(numMatcher.group(0));
            numbers.add(tempNum);
        }

        if(numbers.size() == 0) { return; }
        int gcd = getGcd(numbers);
        tempValue.append(gcd);
        docsAndValues[1].set(index, tempValue.toString());
    }

    public static int getGcd(ArrayList<Integer>  numbers){
        if(numbers.size() == 0){
            return -1;
        }
        int gcd = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            gcd = calcGcdForTwoNum(numbers.get(i), gcd);
        }
        return gcd;
    }

    public static int calcGcdForTwoNum(int a, int b){
        if(a < b){
            calcGcdForTwoNum(b, a);
        }
        if(b == 0){
            return a;
        }
        return calcGcdForTwoNum(b, a % b);
    }


    public static Matcher getMatcher(String regex, String matchingStr){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(matchingStr);
        return matcher;
    }

    public static int getIndex (String regex, String command){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(command);
        matcher.find();
        if(!isValidFileName(matcher.group(1))) { return -1; }
        return docsAndValues[0].indexOf(matcher.group(1));
    }
    public static boolean isValidFileName(String fileName){
        if(docsAndValues[0].contains(fileName)){
            return true;
        }
        else{
            System.out.println("invalid file name!");
            return false;
        }
    }


    public static void commands (){
        Scanner scanner = new Scanner(System.in);
        String command;

        while(true){
            command = scanner.nextLine();
            if (command.matches("^ *ADD DOC .+ *$")){
                makeDoc(command, scanner.nextLine());
            }
            else if(command.matches("^ *END *$")){
                break;
            }
            else if(command.matches("^ *PRINT [^ ]+ *$")){
                printDoc(command);
            }
            else if(command.matches("^ *RMV DOC [^ ]+ *$")){
                removeDoc(command);
            }
            else if(command.matches("^ *RPLC -ALL (?:[\\w]+,)*[\\w]+ [\\w]+ *$")){
                replaceAll(command);
            }
            else if(command.matches("^ *RPLC [^ ]+ (?:[\\w]+,)*[\\w]+ [\\w]+ *$")){
                replaceOne(command);
            }
            else if(command.matches("^ *RMV WORD -ALL [\\w]+ *$")){
                removeAll(command);
            }
            else if(command.matches("^ *RMV WORD [^ ]+ [\\w]+ *$")){
                removeOne(command);
            }
            else if(command.matches("^ *ADD WORD -ALL [\\w]+ *$")){
                addAll(command);
            }
            else if(command.matches("^ *ADD WORD [^ ]+ [\\w]+ *$")){
                addOne(command);
            }
            else if(command.matches("^ *FIND REP [^ ]+ [\\w]+ *$")){
                findRep(command);
            }
            else if(command.matches("^ *GCD [^ ]+ *$")){
                addGcd(command);
            }
            else if(command.matches("^ *FIND MIRROR [^ ]+ [\\w] *$")){
                findMirror(command);
            }
            else if(command.matches("^ *FIND ALPHABET WORDS [^ ]+ *$")){
                findAlphabet(command);
            }
            else{
                System.out.println("invalid command!");
            }
        }
    }


    static ArrayList<String>[]docsAndValues;

    public static void main(String[] args) {
        docsAndValues = new ArrayList[2];
        docsAndValues[0] = new ArrayList<>();//name
        docsAndValues[1] = new ArrayList<>();//value
        commands();
    }
}
/*
ADD DOC 5
1 1 1 1 1 22 22 22 22 3333 3333 3333 AAAA AAAA AA A AAAAAAAAAAAAA AA
FIND REP 5 1
 */
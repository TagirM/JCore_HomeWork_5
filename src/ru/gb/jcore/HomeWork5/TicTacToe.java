//2. Предположить, что числа в исходном массиве из 9 элементов имеют диапазон[0, 3], и представляют собой, например,
// состояния ячеек поля для игры в крестики-нолики, где 0 – это пустое поле, 1 – это поле с крестиком, 2 – это поле
// с ноликом, 3 – резервное значение. Такое предположение позволит хранить в одном числе типа int всё поле 3х3.
// Записать в файл 9 значений так, чтобы они заняли три байта.
//3. (*) - В продолжение 2 дописать "разворачивание" поля игры крестики-нолики из сохраненного в файле состояния
// (распарсить файл, в зависимости от значений (0-3) нарисовать поле со значками 'х' 'о' '.')

package ru.gb.jcore.HomeWork5;

import java.io.*;
import java.util.*;

public class TicTacToe {

    //    Коллекция map используется как словарь для всех возможных сочитаний трех чисел [0-3], в качестве ключа
//    используются символы от 33, в качестве значений сочитание 3 чисел
    private static TreeMap<Character, String> map = new TreeMap<>();

    //  основной метод запуска программы
    public static void main(String[] args) throws IOException {
//      задаем первоначальный массив чисел
        int[] ticTacToe = new int[9];

//      очищаем файл от прежних записей, если он был создан ранее
        clear("src/ru/gb/jcore/HomeWork5/cypherTicTacToe");
//      записываем в файл информацию об игре
        write("src/ru/gb/jcore/HomeWork5/cypherTicTacToe", encode(ticTacToe));
        //  вызов метод построения и вывод в консоль сохраненного в файле игрового поля
        buildGameField(read("src/ru/gb/jcore/HomeWork5/cypherTicTacToe"));
    }

    //    метод записи в файл
    public static void write(String argFilePath, String text) throws IOException {
        File fileStorage = new File(argFilePath);
        if (!fileStorage.exists()) {
            fileStorage.createNewFile();
        }
        BufferedReader tempReader = new BufferedReader(new FileReader(fileStorage));
        List<String> lines = tempReader.lines().toList();
        BufferedWriter fileStream = new BufferedWriter(new FileWriter(fileStorage));
        for (String line :
                lines) {
            fileStream.append(line);
            fileStream.newLine();
        }
        fileStream.write(text);
        fileStream.flush();
        fileStream.close();
        tempReader.close();
    }

    //    метод чтения из файла
    public static String read(String argFilePath) throws IOException {
        File fileStorage = new File(argFilePath);
        BufferedReader fileStream = new BufferedReader(new FileReader(fileStorage));
        String line = "";
        List<String> lines = fileStream.lines().toList();
        if (!lines.isEmpty()) {
            for (int i = 0; i < lines.size(); i++) {
                line = line + lines.get(i);
            }
        } else {
            line = null;
        }
        fileStream.close();
        return line;
    }

    //    метод очистки файла от записей
    public static void clear(String argFilePath) throws IOException {
        File fileStorage = new File(argFilePath);
        BufferedWriter fileStream = new BufferedWriter(new FileWriter(fileStorage));
        fileStream.flush();
        fileStream.close();
    }

    //  метод заполнения коллекции map, которая используется как словарь для всех возможных сочитаний трех чисел [0-3],
    //  в качестве ключа используются символы от 33, в качестве значений сочитание 3 чисел
    public static void mapKey() {
        String value = "";
        char key = 33;
        for (int i = 0; i < 4; i++) {
            for (int l = 0; l < 4; l++) {
                for (int k = 0; k < 4; k++) {
                    value = Integer.toString(i) + Integer.toString(l) + Integer.toString(k);
                    map.put(key, value);
                    key++;
                }
            }
        }
    }

    //  метод кодирования массива из 9 чисел в символьную строку из 3 символов
    public static String encode(int[] ticTacToe) {
        Random random = new Random();
        String[] arr = {"", "", ""};
        int count = 0;
        int j = 0;
//      Заполнение массива случайных чисел [0-3] и заполнение строкового массива этими числами
        while (count < (ticTacToe.length - 1)) {
            for (int i = 0; i < 3; i++) {
                ticTacToe[count] = random.nextInt(4);
                arr[j] = arr[j] + ticTacToe[count];
                count++;
            }
            System.out.println(arr[j]);
            j++;
        }

//      заполняем коллекцию map
        mapKey();

//      кодирование строкового массива с числами в трехсимвольную строку
        char ch = 0;
        String str = "";
        for (int i = 0; i < arr.length; i++) {
            for (char k : map.keySet()) {
                if (map.get(k).equals(arr[i])) {
                    ch = k;
                }
            }
//      получаем строку из 3 символов для будущей записи в файл
            str = str + String.valueOf(ch);
        }
        return str;
    }

    //  раскодирование трехсимвольной записи в массив из 9 чисел в формате String
    public static String[] decode(String cypher) {
        char[] decypher = cypher.toCharArray();
        String str = "";
        for (int i = 0; i < 3; i++) {
            for (char k : map.keySet()) {
                if (k == decypher[i]) {
                    str = str + map.get(k);
                }
            }
        }
        String[] arr = str.split("");
        return arr;
    }

    //  метод построения и вывода в консоль раскодированного из фаила игрового поля
    public static void buildGameField(String cypherFromFile) {
//      строковый раскодированный массив числе из файла
        String[] arr = decode(cypherFromFile);
//      задаем массив чисел, которые были закодированы в файле
        int[] saveGame = new int[9];
//      задаем массив символов игрового поля
        char[] ticTacToeField = new char[9];
//      переводим строковый массив в массив чисел и далее в массив символов игрового поля
        for (int i = 0; i < arr.length; i++) {
            saveGame[i] = Integer.parseInt(arr[i]);
            if (saveGame[i] == 0) ticTacToeField[i] = 'x';
            else if (saveGame[i] == 1) ticTacToeField[i] = '0';
            else if (saveGame[i] == 2) ticTacToeField[i] = '.';
            else ticTacToeField[i] = 'r';
        }
        int count = 0;
//      выводим игровое поле в консоль
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(ticTacToeField[count] + " ");
                count++;
            }
            System.out.println();
        }
    }
}

import java.time.Duration;
import java.time.Instant;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.Math.max;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * @author NesioIV
 * @version 1.0
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
/*
   реализуется задача нахождения наибольшего общего делителя всех чисел
   в заданном диапазоне [L, R]

   описание решения задачи приведено в файле ..\README.md
*/
class NumRangeGcd {

   // поля-переменные общего доступа
   static int resNumLeft = 0;  // первый искомый аргумент НОД
   static int resNumRight = 0;  // второй искомый аргумент НОД
   static int resGcd = 0;  // искомый НОД диапазона

   /*
      метод поиска НОД
      numLeft, numRight - границы дипазона целых чисел
      1 < numLeft <= numRight <= 2147483647
   */
   static void SearchGcd (int numLeft, int numRight) {
      int rndDownNumLeft = numLeft;  // округляемая вверх левая граница
      int rndUpNumRight = numRight;  // округляемая вниз правая граница
      if (rndDownNumLeft * 2 < rndUpNumRight) {
         if (rndUpNumRight % 2 == 0) {  // в этом случае решение тривиальное
            resNumRight = rndUpNumRight;
         }
         else
         {
            resNumRight = rndUpNumRight - 1;
         }
         resNumLeft = rndUpNumRight / 2;
         resGcd = resNumLeft;
      }
      else {
         if (rndDownNumLeft < rndUpNumRight) {
            for (int i = 1; rndDownNumLeft <= rndUpNumRight; i++) {
               // округлить в большую сторону до ближайшего целого, кратного i
               rndDownNumLeft = (numLeft / i + Integer.signum(numLeft % i)) * i;
               // округлить в меньшую сторону до ближайшего целого, кратного i
               rndUpNumRight = (numRight / i) * i;
               // зафиксировать очередной результат, пока сужаемые границы
               // поиска НОД не совпали
               if (rndDownNumLeft < rndUpNumRight) {
                  resGcd = max(resGcd, Gcd(rndUpNumRight, rndDownNumLeft));
                  resNumLeft = rndDownNumLeft;
                  resNumRight = rndUpNumRight;
               }
            }
         } else {  // если указанные границы диапазона совпают, то решение тривиальное
            resNumLeft = rndDownNumLeft;
            resNumRight = rndUpNumRight;
            resGcd = resNumLeft;
         }
      }
   }

   /*
      метод нахождения НОД двух целых положительных чисел
      по алгоритму Евклида с использованием рекурсии
   */
   static int Gcd (int p, int q) {
      if (q == 0) {
         return p;
      }
      return Gcd(q, p % q);
   }

   /*
      главный метод класса
      - реализует консольный интерфейс с пользователем
      - принимает ввод исходных данных, контролирует ограничения
        вводимых данных на допустимость
      - вызывает метод поиска НОД диапазона
      - выводит результаты работы приложения в консоль
      - использует класс java.util.Scanner
   */
   public static void main(String[] args) {

      System.out.println("\n" + "<Наибольший общий делитель диапазона чисел> NesioIV, 2022" + "\n");
      int numLeft = 0;
      int numRight = 0;
      System.out.print("Введите начало диапазона A (целое число 1 < A <= 2147483647): " + "\n");
      while (numLeft <= 1) {
         Scanner scan = new Scanner(System.in);
         try {
            numLeft = scan.nextInt();
            if (numLeft <= 1) {
               System.err.println("Ввод некорректен. Введите начало диапазона A (целое число 1 < A <= 2147483647): ");
            }
         } catch (InputMismatchException e) {
            System.err.println("Ввод некорректен. Введите начало диапазона A (целое число 1 < A <= 2147483647): ");
            numLeft = 0;
         }
      }
      System.out.printf("Введите конец диапазона B (целое число %d <= B <= 2147483647): %n", numLeft);
      while (numLeft > numRight) {
         Scanner scan = new Scanner(System.in);
         try {
            numRight = scan.nextInt();
            if (numLeft > numRight) {
               System.err.printf("Ввод некорректен. Введите конец диапазона B (целое число %d <= B <= 2147483647): %n", numLeft);
            }
         } catch (InputMismatchException e) {
            System.err.printf("Ввод некорректен. Введите конец диапазона B (целое число %d <= B <= 2147483647): %n", numLeft);
            numRight = 0;
         }
      }
      System.out.println("\n" + "Выполняется поиск наибольшего общего делителя (НОД) диапазона чисел..." + "\n");
      Instant startTime = Instant.now();
      SearchGcd(numLeft, numRight);
      Instant stopTime = Instant.now();
      System.out.printf("%-25s %-25s", "Начало диапазона А", "Конец диапазона B");
      System.out.printf("%n%-25d %-25d", numLeft, numRight);
      System.out.printf("%n%-25s %-25s %-25s", "Найденный НОД диапазона", "Первый аргумент НОД", "Второй аргумент НОД");
      System.out.printf("%n%-25d %-25d %-25d", resGcd, resNumLeft, resNumRight);
      System.out.printf("%n%-25s", "Время выполнения, мс");
      System.out.printf("%n%-25d", Duration.between(startTime, stopTime).toMillis());
      System.out.println("\n");
      System.out.println("= Выполнение завершено =");
   }
}



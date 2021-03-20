package utils;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static int getInt()
    {
        Scanner in = new Scanner(System.in);
        String s = null;
        Pattern p = Pattern.compile("^\\d+$");
        Matcher m = null;
        boolean first = true;

        do
        {
            if (first) first = false;
            else System.out.print("[E] Введите значение: ");

            s = in.next();
            m = p.matcher(s);
        } while(!m.matches() || Integer.parseInt(s) < 0);

        return Integer.parseInt(s);
    }

    public static int getIntMinMax(int min, int max)
    {
        Scanner in = new Scanner(System.in);
        String s = null;
        Pattern p = Pattern.compile("^\\d+$");
        Matcher m = null;
        boolean first = true;

        do
        {
            if (first) first = false;
            else System.out.print("[E] Введите значение [" + min + "-" + max + "]:  ");

            s = in.next();
            m = p.matcher(s);
        } while(!m.matches() || Integer.parseInt(s) > max || Integer.parseInt(s) < min);

        return Integer.parseInt(s);
    }
}

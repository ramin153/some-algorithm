import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PasswordPolicy {
/*
1-3 a: abcde
1-3 b: cdefg
2-9 c: ccccccccc
Each line gives the password policy and then the password. The password policy indicates the lowest and highest number of times a given letter must appear for the password to be valid. For example, 1-3 a means that the password must contain a at least 1 time and at most 3 times.

In the above example, 2 passwords are valid. The middle password, cdefg, is not; it contains no instances of b, but needs at least 1. The first and third passwords are valid: they contain one a or nine c, both within the limits of their respective policies.

How many passwords are valid according to their policies?

--- Part Two ---
While it appears you validated the passwords correctly, they don't seem to be what the Official Toboggan Corporate Authentication System is expecting.

The shopkeeper suddenly realizes that he just accidentally explained the password policy rules from his old job at the sled rental place down the street! The Official Toboggan Corporate Policy actually works a little differently.

Each policy actually describes two positions in the password, where 1 means the first character, 2 means the second character, and so on. (Be careful; Toboggan Corporate Policies have no concept of "index zero"!) Exactly one of these positions must contain the given letter. Other occurrences of the letter are irrelevant for the purposes of policy enforcement.

Given the same example list from above:

1-3 a: abcde is valid: position 1 contains a and position 3 does not.
1-3 b: cdefg is invalid: neither position 1 nor position 3 contains b.
2-9 c: ccccccccc is invalid: both position 2 and position 9 contain c.
How many passwords are valid according to the new interpretation of the policies?
*/


    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("input2.txt");
        Scanner myReader = new Scanner(file);
        partTwo(myReader);



    }
    public static void partOne(Scanner myReader)
    {
        int result = 0;
        while (myReader.hasNextLine())
        {
            String[] line = myReader.nextLine().split("\\s+");
            int low = Integer.parseInt(line[0].split("-")[0]);
            int high = Integer.parseInt(line[0].split("-")[1]);
            int count = 0;
            char item = line[1].charAt(0);
            String sentence =  line[2];
            for (int i =0;i < sentence.length();i++)
                if (sentence.charAt(i) == item)
                    count++;
            if (count >= low && count <= high)
                result++;
        }
        System.out.println(result);
    }
    public static void partTwo(Scanner myReader)
    {
        int result = 0;
        while (myReader.hasNextLine())
        {
            String[] line = myReader.nextLine().split("\\s+");
            int pos1 = Integer.parseInt(line[0].split("-")[0]);
            int pos2 = Integer.parseInt(line[0].split("-")[1]);

            char item = line[1].charAt(0);
            String sentence =  line[2];
            if ((sentence.charAt(pos1-1) == item) ^ (sentence.charAt(pos2-1) == item))
                result++;

        }
        System.out.println(result);
    }
}

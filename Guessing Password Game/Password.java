import java.util.*;

public class Password {
    private String actualPassword;

    // Pembuatan password 
    public Password() {
        generatePassword();
    }

    // Method untuk generate 3 digit password yang bersifat unik
    private void generatePassword() {

        // Membuat digitsSet untuk memastikan digit bersifat unik
        Set<Integer> digitsSet = new HashSet<>();
        Random random = new Random();

        // Looping hingga mendapatkan 3 digit password yang unik
        while (digitsSet.size() < 3) {
            int digit = random.nextInt(10);
            digitsSet.add(digit);
        }

        // Membuat digitsSet menjadi string
        StringBuilder passwordBuilder = new StringBuilder();
        for (int digit : digitsSet) {
            passwordBuilder.append(digit);
        }

        actualPassword = passwordBuilder.toString();
    }

    // Method untuk melakukan pengecekan terhadap password yang ditebak user
    public String checkGuess(String guess) {

        // Starting value jumlah digit dan posisi yang benar
        int trueDigits = 0;
        int truePositions = 0;

        // Melakukan looping untuk informasi jumlah digit yang benar dan jumlah posisi digit yang benar
        for (int i = 0; i < actualPassword.length(); i++) {
            if (actualPassword.charAt(i) == guess.charAt(i)) {
                truePositions++;
            }
            if (actualPassword.contains(String.valueOf(guess.charAt(i)))) {
                trueDigits++;
            }
        }
        return "\n" + trueDigits + " digit(s) correct.\n" + truePositions + " correct position(s).";
    }

    // Method untuk mengecek apakah password yang ditebak user sama dengan password yang digenerate program
    public boolean isPasswordGuessed(String guess) {
        return actualPassword.equals(guess);
    }

    // Method toString
    @Override
    public String toString() {
        return actualPassword;
    }
}
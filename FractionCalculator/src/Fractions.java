class Fractions {
    public int numerator; // числитель
    public int denominator; // знаменатель

    public Fractions(int numerator, int denominator) throws IllegalArgumentException {
        if (denominator == 0) throw new IllegalArgumentException("Знаменатель не может быть равен 0");
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public static Fractions parse(Object token) throws IllegalArgumentException {
        String[] numbers = token.toString().replace(" ", "").split("/");
        try {
            int denominator = Integer.parseInt(numbers[1]);
            if (denominator == 0) throw new IllegalArgumentException("Деление на 0");
            int numerator = Integer.parseInt(numbers[0]);
            return Fractions.simplify(new Fractions(numerator, denominator));
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Некорректное выражение");
        }
    }

    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }

    public static Fractions simplify(Fractions fraction) {
        if (fraction.denominator == 0) {
            System.out.print("Знаменатель ноль, тогда используем дробь по умолчанию\n");
            fraction.numerator = 1;
            fraction.denominator = 1;
        } else if (fraction.denominator < 0) {
            fraction.numerator *= -1;
            fraction.denominator *= -1;
        }

        int numGCD = Math.abs((GCD(fraction.numerator, fraction.denominator)));
        fraction.numerator /= numGCD;
        fraction.denominator /= numGCD;

        return (fraction);
    }

    public static int GCD(int a, int b) {
        while (b != 0) {
            int tmp = a % b;
            a = b;
            b = tmp;
        }
        return a;
    }

    public static Fractions Multiplication(Fractions fraction1, Fractions fraction2) {
        Fractions tmpFraction = new Fractions(1, 1);
        tmpFraction.numerator = fraction1.numerator * fraction2.numerator;
        tmpFraction.denominator = fraction1.denominator * fraction2.denominator;
        Fractions.simplify(tmpFraction);
        return (tmpFraction);
    }

    public static Fractions Division(Fractions fraction1, Fractions fraction2) {
        Fractions tmpFraction = new Fractions(1, 1);
        tmpFraction.numerator = fraction1.numerator * fraction2.denominator;
        tmpFraction.denominator = fraction1.denominator * fraction2.numerator;
        Fractions.simplify(tmpFraction);
        return (tmpFraction);
    }

    public static Fractions Addition(Fractions fraction1, Fractions fraction2) {
        Fractions tmpFraction = new Fractions(1, 1);
        tmpFraction.numerator = fraction1.numerator * fraction2.denominator + fraction2.numerator * fraction1.denominator;
        tmpFraction.denominator = fraction1.denominator * fraction2.denominator;
        Fractions.simplify(tmpFraction);
        return (tmpFraction);
    }

    public static Fractions Subtraction(Fractions fraction1, Fractions fraction2) {
        Fractions tmpFraction = new Fractions(1, 1);
        tmpFraction.numerator = fraction1.numerator * fraction2.denominator - fraction2.numerator * fraction1.denominator;
        tmpFraction.denominator = fraction1.denominator * fraction2.denominator;
        Fractions.simplify(tmpFraction);
        return (tmpFraction);
    }
}
import coll.Insertion;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 36265&36265/100008 [(cosh(sinh(log2(φ))-(pi^2))+cosh((φ/cos(pi)^2)+log(3-tan(φ)))-(signum(d)+-b))]
 * 35296&35296/100008 [(cosh(φ*log(pi)-sinh(pi))+cosh((φ/cos(pi)^2)+log(3-tan(φ)))-(signum(d)+-b))]
 * 32868&32868/100008 [((φ%0^log2(1)^2)-signum(tan(e/(log10(b)^2)*(φ^2))/log10(φ%log(c)))*(log2(pi)/log10(log2(pi))^(φ^3)--d))]
 * 32803&32803/100008 [φ/tan(1)*sinh((e^2))-(signum(s)^2)+d/(signum(a)^3)^3^log(e)-0]
 * 32727&32727/100008 [((1-c)%(log(1/cbrt(pi))/log10(x))-(exp(sinh(e))+c))] == org == ((-c+1+0)%(log(signum(cos(-1))/cbrt(--pi)%cbrt(1))/log10(x))-(exp(sinh(--e))+c))
 * 27435&27435/100008 [-a+sqrt(signum(log2(c)))-(pi^3)] == org == [(0/((log(tan(1)))+(cbrt(c)))*(log10(1))+(((signum(((sqrt(log2(c))))))-(pi^3)))-(((1+a)-1)))]
 * 27161&27161/100008 [((exp(exp(sqrt(pi)))+d)*(cosh(-1)-exp(-1))^(tan(log2(a))*cos(a)/cbrt(sinh(d)))^(log(exp(cbrt(cbrt(c)))+sin(a)))%(cosh(x)+sin(-a)))]
 * 24928&27435/100008 [((-1/(sinh(x))-b)-((exp(e))%(exp(a)))*(1+(log(e))))]
 * 24523&24523/100008 [(((signum(b)%cosh(e)))/((((((sinh((sinh(1)%cos(1)))))^3))))+(((((signum(b)%(a^2))^3))+b))-(1+exp(-b)))]
 * <p>
 * /**
 * WHOMA!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * Found: 36265 of total 100008. All hits: 36265
 * Exp: (cosh(sinh(log2(φ))-(pi^2))+cosh((φ/cos(pi)^2)+log(3-tan(φ)))-(signum(d)+-b))
 */
public class Hack {

    private static final String FILE_NAME = "files/primes1k.txt";
    private static Set<Integer> primesSet = new LinkedHashSet<>();
    private static List<Integer> primesList = new ArrayList<>();
    private static final int OPS = 1_000_000;
    private static final Set<String> varSet = new HashSet<String>() {{
        add("a");
        add("b");
        add("c");
        add("d");
        add("x");
        add("s");
    }};

    private static void init() throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream(FILE_NAME);
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);
        for (String line; (line = reader.readLine()) != null; ) {
            String[] row = line.split(" ");
            for (String i : row) {
                if (!i.trim().isEmpty()) {
                    primesSet.add(Integer.valueOf(i.trim()));
                    primesList.add(Integer.valueOf(i.trim()));
                }
            }
        }
        System.out.println("Loaded " + primesList.size() + " primes [" + primesList.get(0) +
                "," + primesList.get(primesList.size() - 1) + "]");
    }

    public static void main(String[] args) throws IOException {
        init();

        long start = System.currentTimeMillis();
        for (int y = 1; y < 1_000_000_000; y++) {
            if (y % OPS == 0) {
                double timeElapsed = (System.currentTimeMillis() - start) / 1000.0;
                System.out.println("Executed " + OPS + " operations in " + timeElapsed + " seconds, " + (OPS / timeElapsed) + " per second.");
                start = System.currentTimeMillis();
            }
            // Choose an expression.
            String exp = "";
            Random r = new Random();
            int choice = r.nextInt(4);
            if (choice == 0) {
                exp = getInnerExpression(getExpression()) + getArithmetic() + getInnerExpression(getExpression()) + getArithmetic() +
                        getInnerExpression(getExpression()) + getArithmetic() + getInnerExpression(getExpression()) + getArithmetic() +
                        getInnerExpression(getExpression()) + getArithmetic() + getInnerExpression(getExpression());
            } else if (choice == 1) {
                exp = getInnerExpression(getExpression()) + getArithmetic() + getExpression() + getArithmetic() + getExpression() + getArithmetic()
                        + getExpression() + getArithmetic() + getInnerExpression(getExpression() + getArithmetic() + getExpression());
            } else if (choice == 2) {
                exp = getInnerExpression(getExpression()) + getArithmetic() + getExpression() + getArithmetic() +
                        getInnerExpression(getInnerExpression(getInnerExpression(getInnerExpression(getInnerExpression(getExpression()))) + getArithmetic() +
                                getInnerExpression(getInnerExpression(getInnerExpression(getExpression())) + getArithmetic() + getInnerExpression(getInnerExpression(getExpression()))) + getArithmetic() +
                                getInnerExpression(getExpression() + getArithmetic() + getExpression())))
                        + getArithmetic() + getInnerExpression(getExpression() + getArithmetic() + getExpression());
            } else {
                exp = getInnerExpression(getExpression()) + getArithmetic() + getExpression() + getArithmetic() +
                        getInnerExpression(getExpression(getExpression(getExpression()) + getArithmetic() + getExpression()) + getArithmetic()
                                + getExpression(getConstant() + getArithmetic() + getExpression(getExpression()) + getArithmetic() + getExpression(getConstant() + getArithmetic() + getExpression())))
                        + getArithmetic() + getInnerExpression(getExpression() + getArithmetic() + getExpression())
                        + getArithmetic() + getInnerExpression(getExpression() + getArithmetic() + getExpression());
            }
//            if (!operationSet.add(exp)) {
//                continue;
//            }
            Expression expression = null;
            try {
                expression = new ExpressionBuilder(exp)
                        .variables(varSet)
                        .build();
            } catch (Exception ex) {
                System.out.println("Run number: " + y);
                ex.printStackTrace();
            }
            Set<Insertion> col = new LinkedHashSet<>();
            int failCount = 0, hits = 0, primePredictionFailCount = 0, divisionByZero = 0;
            int s = primesList.get(0) + primesList.get(1) + primesList.get(2);
            for (int x = 4; x < primesList.size(); x++) {
                int a = primesList.get(x - 1);
                int b = primesList.get(x - 2);
                int c = primesList.get(x - 3);
                int d = primesList.get(x - 4);
                s += a;
                expression.setVariable("a", a);
                expression.setVariable("b", b);
                expression.setVariable("c", c);
                expression.setVariable("d", d);
                expression.setVariable("x", x);
                expression.setVariable("s", s);
                if (primePredictionFailCount > 40) {
//                    System.out.println("Failed to predict 40 primes in row.");
                    break;
                } else if (failCount > 40 || divisionByZero > 20) {
//                    System.out.println("Too many fails.");
                    break;
                } else if (x > 1000 && col.size() < 100) {
//                    System.out.println("Too few found, give up.");
                    break;
                }
                double dou;
                try {
                    dou = expression.evaluate();
                } catch (ArithmeticException ee) {
                    if (ee.getMessage().startsWith("Division")) {
                        divisionByZero++;
                    }
                    failCount++;
                    continue;
                } catch (IllegalArgumentException ee) {
                    failCount++;
                    System.out.println("Illegal exception argument for: " + y + ", " + exp);
                    continue;
                } catch (Exception ee) {
                    System.out.println("Unsupported exception: " + ee.getCause());
                    failCount++;
                    continue;
                } catch (OutOfMemoryError ee) {
                    ee.printStackTrace();
                    continue;
                }
                int v = Math.abs((int) Math.round(dou));
                if (primesSet.contains(v)) {
                    hits++;
                    if (v > a) {
                        col.add(new Insertion(x, a, b, c, v));
                    }
                    primePredictionFailCount = 0;
                } else {
                    primePredictionFailCount++;
                }
            }
            if (col.size() > 426) {
                System.out.println();
                if (col.size() > 36265) {
                    System.out.println("WHOMA!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                }
                System.out.println("Found: " + col.size() + " of total " + primesList.size() + ". All hits: " + hits + ". Run number: " + y);
                System.out.println("Exp: " + exp + ", val: " + printSet(col));
                System.out.println();
            }
        }
    }

    private static String printSet(Set<?> set) {
        StringBuilder sb = new StringBuilder();
        int n = 0;
        Insertion insertion = null;
        Iterator i = set.iterator();
        while (i.hasNext()) {
            n++;
            insertion = (Insertion) i.next();
            if (n < 1000) {
                sb.append(insertion + ", ");
            }
        }
        return "Last was: " + insertion + ", " + sb.toString();
    }

    private static String getInnerExpression(String val) {
        Random x = new Random();
        int v = x.nextInt(700);
        if (v < 10) {
            val = val + getArithmetic() + getInnerExpression(getExpression());
        } else if (v < 30) {
            val = val + getArithmetic() + getExpression();
        } else if (v < 70) {
            val = getExpression(val) + getArithmetic() + getExpression();
        } else if (v < 200) {
        } else if (v < 210) {
            val = getInnerExpression(getExpression(val));
        } else if (v < 310) {
            val = getConstant() + getArithmetic() + val;
        } else if (v < 320) {
            val = getExpression() + getArithmetic() + getExpression();
        } else if (v < 330) {
            val = getInnerExpression(getExpression(val)) + getArithmetic() + getExpression(val);
        } else if (v < 340) {
            val = getInnerExpression(getExpression(val)) + getArithmetic() + getInnerExpression(getExpression());
        } else {
            val = getExpression(val);
        }
        return val;
    }

    private static String getExpression() {
        Random r = new Random();
        String val = "";
        switch (r.nextInt(8)) {
            case 0:
                val = "a";
                break;
            case 1:
                val = "b";
                break;
            case 2:
                val = "c";
                break;
            case 3:
                val = "x";
                break;
            case 4:
                val = getConstant();
                break;
            case 5:
                val = getConstant();
                break;
            case 6:
                val = "d";
                break;
            case 7:
                val = "s";
                break;
        }
        r = new Random();
        if (r.nextInt(10) == 9) {
            val = "-" + val;
        }
        return getExpression(val);
    }

    private static String getExpression(String val) {
        Random r = new Random();
        switch (r.nextInt(17)) {
            case 0:
                val = "sqrt(" + val + ")";
                break;
            case 1:
                val = "log(" + val + ")";
                break;
            case 2:
                val = "log10(" + val + ")";
                break;
            case 3:
                val = "log2(" + val + ")";
                break;
            case 4:
                val = "cos(" + val + ")";
                break;
            case 5:
                val = "sin(" + val + ")";
                break;
            case 6:
                val = "signum(" + val + ")";
                break;
            case 7:
                val = "cbrt(" + val + ")";
                break;
            case 8:
                val = "exp(" + val + ")";
                break;
            case 9:
                val = "tan(" + val + ")";
                break;
            case 10:
                val = "cosh(" + val + ")";
                break;
            case 11:
                val = "sinh(" + val + ")";
                break;
            case 12:
                val = "(" + val + "^2)";
                break;
            case 13:
                val = "(" + val + "^3)";
                break;
            case 14:
                val = "0";
                break;
            case 15:
                val = "1";
                break;
            case 16:
                break;
            default:
                break;
        }
        return val;
    }

    private static String getConstant() {
        Random r = new Random();
        switch (r.nextInt(6)) {
            case 0:
                return "φ";
            case 1:
                return "e";
            case 2:
                return "pi";
            case 3:
                return "1";
            case 4:
                return "0";
            case 5:
                return "3";
            default:
                return "";
        }
    }

    private static String getArithmetic() {
        Random r = new Random();
        switch (r.nextInt(6)) {
            case 0:
                return "*";
            case 1:
                return "/";
            case 2:
                return "+";
            case 3:
                return "-";
            case 4:
                return "%";
            case 5:
                return "^";
            default:
                return "%";
        }
    }
}

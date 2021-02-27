package attacks.traditional;

import crypto.traditional.FourSquareCipher;
import util.TextFitnessComputer;

public class FourSquareSolver {
    private final static String ALPHABET = "ABCDEFGHIKLMNOPQRSTUVWXYZ";

    public static void main(String[] args) {
        String[] key = new String[2];
        key[0] = shuffle("ABCDEFGHIKLMNOPQRSTUVWXYZ");
        key[1] = shuffle("ABCDEFGHIKLMNOPQRSTUVWXYZ");
        System.out.println("key[0] = " + key[0]);
        System.out.println("key[1] = " + key[1]);
        String c = new FourSquareCipher(key).encrypt(
                "THEFIRSTSIXTYCHARACTERSISTHESOLUTIONITHANKTHEACMFORTHISAWARDICANTHELPBUTFEELTHATIAMRECEIVINGTHISHONORFORTIMINGANDSERENDIPITYASMUCHASTECHNICALMERITUNIXSWEPTINTOPOPULARITYWITHANINDUSTRYWIDECHANGEFROMCENTRALMAINFRAMESTOAUTONOMOUSMINISISUSPECTXHATDANIELBOBROWXOULDBEHEREINSTEADOFMEIFHECOULDNOTAFXORDAPDPANDHADHADTOSETXLEFORAPDPMOREOVERTHECURXENTSTATEOFUNIXISTHERESULTOFTHELABORSOFALARGENUMBEROFPEOPLETHEREISANOLDADAGEDANCEWITHTHEONETHATBROUGHTYOUWHICHMEANSTHATISHOULDTALKABOUTUNIXIHAVENOTWORKEDONMAINSTREAMUNIXINMANYYEARSYETICONTINUETOGETUNDESERVEDCREDITFORTHEWORKOFOTHERSTHEREFOREIAMNOTGOINGTOTALKABOUTUNIXBUTIWANTXOTHANKEVERYONEWHOHASCONTRIBUTEDTHATBRINGSMETODENNISRITCHIEOURCOLLABORATIONHASBEXNATHINGOFBEAUTYINTHETENYEARSTHATWEHAVEWORKEDTOGETHERICANRECALXONLYONECASEOFMISCOORDINATIONOFWORKONTHATOCCASIONIDISCOVEREDTHATWEBOTHXADWRITTENTHESAMELINEASSEMBLYLANGUAGEPROGRAMICOMPAREDTHESOURCESANDWASASTOUNDEDTOFINDTHATXHEYMATCHEDCHARACTERFORCHARACTERTHERESULTOFOURWORKTOGETHERHASBEENFARGREATERTHANTHEWORKTHATWEXACHCONTRIBUTEDINCOLXEGEBEFOREVIDEOGAMESWEWOULDAMUSEOURSELVESBYPOSINGPROGRAMXINGEXERCISESONEOFTHEFAVORITESWASTOWRITETHESHORTESTSELFREPRODUCINGPROGRAMSINCETHISISANEXERCISEDIVORCEDFROMREALITYTHEUSUALVEHICLEWASFORTRANACTUALXYFORTRANWASTHELANGUAGEOFCHOICEFORTHESAMEREASONTHATTHREELEGGEDRACESAREPOPULARMOREPRECISELYSTATEDTHEPROBLEMISTOWRITEASOURCEPROGRAMTHATWHENCOMPILEDANDEXECUTEDWILLPRODUCEASOUTPUTANEXACTCOPYOFITSSOURCEIFYOUHAVENEVERDONETHISIURGEYOUTOTRYITONYOUROWNTHEDISCOVERYOFHOWTODOITISAREVELATIONTHATFARSURPASXESANYBENEFITOBTAINEDBYBEINGTOLDHOWTODOITTHEPARTABOUTSHORTESTWASIUSTANINCENTIVETODEMONSTRATESKILXANDXETERMINEAWINNERFIGURESHOWSASELFREPRODUCINGPROGRAMINTHECPROGRAMMINGLANGUAGETHEPURISTWILLNOTETHATXHEPROGRAMISNOTPRECISELYASELFREPRODUCINGPROGRAMBUTWILLPRODUCEASELFREPRODUCINGPROGRAMTHISENTRYISMUCHTOOLARGETOWINAPRIZEBUTITDEMONSTRATESTHETECHNIQUEANDHASTWOIMPORTANTPROPERTIESTHATINEXDTOCOMPLETEMYSTORYTHISPROGRAMCANBEEASILYWRITTENBYANOTHERPROGRAMTHISPROGRAMCANCONTAINANARBITRARYAMOUNTOFEXCESSBAGGAGETHATWILXBEREPRODUCEDALONGWITHTHEMAINALGORITHMINTHEEXAMPLEXVENTHECOMXENTISREPRODUCEDX");

        String[] foundKey;
        long total = 0;
        for (int i = 0; i < 20; i++) {
            long start = System.nanoTime();
            foundKey = findKey(c);
            String p = new FourSquareCipher(foundKey).decrypt(c);
            long elapsed = (System.nanoTime() - start) / 1000000;
            System.out.println(elapsed + "\t" + TextFitnessComputer.getInstance().computeScore(p) + "\t" + p);
            total += elapsed;
        }

        System.out.println(total / 20.0);
    }

    public static String[] findKey(String ciphertext) {
        String[] keys = new String[2];
        String[] candidateKeys = new String[2];
        keys[0] = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
        keys[1] = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
        candidateKeys[0] = keys[0];
        candidateKeys[1] = keys[1];
        String plaintext = new FourSquareCipher(keys).decrypt(ciphertext);
        double prob = 0;
        double dF = 0;
        double score = TextFitnessComputer.getInstance().computeScore(plaintext);
        double bestScore = score;
        for (double t = 20; t >= 0; t = t - 0.2) {
            for (int i = 0; i < 200; i++) {
                candidateKeys[0] = modifyKey(keys[0]);
                candidateKeys[1] = modifyKey(keys[1]);
                plaintext = new FourSquareCipher(candidateKeys).decrypt(ciphertext);
                score = TextFitnessComputer.getInstance().computeScore(plaintext);
                dF = score - bestScore;
                if (dF >= 0) {
                    keys[0] = candidateKeys[0];
                    keys[1] = candidateKeys[1];
                    bestScore = score;
                } else if (t > 0) {
                    prob = Math.exp(dF / t);
                    if (prob > Math.random()) {
                        keys[0] = candidateKeys[0];
                        keys[1] = candidateKeys[1];
                        bestScore = score;
                    }
                }

            }
        }

        return new String[]{keys[0], keys[1]};
    }

    public static String modifyKey(String key) {
        int rand = (int) (50 * Math.random());
        if (rand == 0) {
            return swap2Columns(key);
        } else if (rand == 1) {
            return swap2Rows(key);
        } else if (rand == 2) {
            return upDown(key);
        } else if (rand == 3) {
            return leftRight(key);
        } else if (rand == 4) {
            return reverse(key);
        } else {
            return swap2Letters(key);
        }

    }

    public static String swap2Columns(String key) {
        StringBuilder sb = new StringBuilder(key);
        int c1 = (int) (5 * Math.random());
        int c2 = (int) (5 * Math.random());
        char temp;
        for (int i = 0; i < 5; i++) {
            temp = sb.charAt(c1 + 5 * i);
            sb.setCharAt(c1 + 5 * i, sb.charAt(c2 + 5 * i));
            sb.setCharAt(c2 + 5 * i, temp);
        }

        return sb.toString();
    }

    public static String swap2Rows(String key) {
        StringBuilder sb = new StringBuilder(key);
        int r1 = (int) (5 * Math.random());
        int r2 = (int) (5 * Math.random());
        char temp;
        for (int i = 0; i < 5; i++) {
            temp = sb.charAt(5 * r1 + i);
            sb.setCharAt(5 * r1 + i, sb.charAt(5 * r2 + i));
            sb.setCharAt(5 * r2 + i, temp);
        }

        return sb.toString();
    }

    public static String upDown(String key) {
        StringBuilder sb = new StringBuilder(key);
        char temp;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                temp = key.charAt(5 * i + j);
                sb.setCharAt(5 * i + j, sb.charAt(5 * (4 - i) + j));
                sb.setCharAt(5 * (4 - i) + j, temp);
            }

        }

        return sb.toString();
    }

    public static String leftRight(String key) {
        StringBuilder sb = new StringBuilder(key);
        char temp;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                temp = key.charAt(5 * j + i);
                sb.setCharAt(5 * j + i, sb.charAt(5 * j + 4 - i));
                sb.setCharAt(5 * j + 4 - i, temp);
            }

        }

        return sb.toString();
    }

    public static String reverse(String key) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < key.length(); i++) {
            sb.append(key.charAt(key.length() - i - 1));
        }

        return sb.toString();
    }

    public static String swap2Letters(String key) {
        int r1 = (int) (25 * Math.random());
        int r2 = (int) (25 * Math.random());
        StringBuilder sb = new StringBuilder(key);
        char temp = sb.charAt(r1);
        sb.setCharAt(r1, sb.charAt(r2));
        sb.setCharAt(r2, temp);

        return sb.toString();
    }

    public static String shuffle(String key) {
        int r;
        char temp;
        StringBuilder sb = new StringBuilder(key);
        for (int i = 24; i >= 1; i--) {
            r = (int) (i * Math.random());
            temp = sb.charAt(r);
            sb.setCharAt(r, sb.charAt(i));
            sb.setCharAt(i, temp);
        }

        return sb.toString();
    }
}

package com.github.berkbavas.crypto.attacks.traditional;

import com.github.berkbavas.crypto.ciphers.traditional.MonoalphabeticCipher;
import com.github.berkbavas.crypto.util.Stopwatch;
import com.github.berkbavas.crypto.util.TextFitnessCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MonoalphabeticCipherSolverTest {

    @Test
    void testFindKey() {
        System.out.println("MonoalphabeticCipherSolverTest.java: Test is started.");

        String plaintext = "TWENTYYEARSAGOLAWENFORCEMENTORGANIZATIONSLOBBIEDTOREQUIREDATAANDCOMMUNICATIONSERVICESTOENGINEERTHEIRPRODUCTSTOGUARANTEELAWENFORCEMENTACCESSTOALLDATAAFTERLENGTHYDEBATEANDVIGOROUSPREDICTIONSOFENFORCEMENTCHANNELSGOINGDARKTHESEATTEMPTSTOREGULATETHEEMERGINGINTERNETWEREABANDONEDINTHEINTERVENINGYEARSINNOVATIONONTHEINTERNETFLOURISHEDANDLAWENFORCEMENTAGENCIESFOUNDNEWANDMOREEFFECTIVEMEANSOFACCESSINGVASTLYLARGERQUANTITIESOFDATATODAYWEAREAGAINHEARINGCALLSFORREGULATIONTOMANDATETHEPROVISIONOFEXCEPTIONALACCESSMECHANISMSINTHISREPORTAGROUPOFCOMPUTERSCIENTISTSANDSECURITYEXPERTSMANYOFWHOMPARTICIPATEDINASTUDYOFTHESESAMETOPICSHASCONVENEDTOEXPLORETHELIKELYEFFECTSOFIMPOSINGEXTRAORDINARYACCESSMANDATESWEHAVEFOUNDTHATTHEDAMAGETHATCOULDBECAUSEDBYLAWENFORCEMENTEXCEPTIONALACCESSREQUIREMENTSWOULDBEEVENGREATERTODAYTHANITWOULDHAVEBEENYEARSAGOINTHEWAKEOFTHEGROWINGECONOMICANDSOCIALCOSTOFTHEFUNDAMENTALINSECURITYOFTODAYSINTERNETENVIRONMENTANYPROPOSALSTHATALTERTHESECURITYDYNAMICSONLINESHOULDBEAPPROACHEDWITHCAUTIONEXCEPTIONALACCESSWOULDFORCEINTERNETSYSTEMDEVELOPERSTOREVERSEFORWARDSECRECYDESIGNPRACTICESTHATSEEKTOMINIMIZETHEIMPACTONUSERPRIVACYWHENSYSTEMSAREBREACHEDTHECOMPLEXITYOFTODAYSINTERNETENVIRONMENTWITHMILLIONSOFAPPSANDGLOBALLYCONNECTEDSERVICESMEANSTHATNEWLAWENFORCEMENTREQUIREMENTSARELIKELYTOINTRODUCEUNANTICIPATEDHARDTODETECTSECURITYFLAWSBEYONDTHESEANDOTHERTECHNICALVULNERABILITIESTHEPROSPECTOFGLOBALLYDEPLOYEDEXCEPTIONALACCESSSYSTEMSRAISESDIFFICULTPROBLEMSABOUTHOWSUCHANENVIRONMENTWOULDBEGOVERNEDANDHOWTOENSURETHATSUCHSYSTEMSWOULDRESPECTHUMANRIGHTSANDTHERULEOFLAW";
        int numberOfAttacks = 20;
        int success = 0;
        double elapsed = 0;

        for (int i = 0; i < numberOfAttacks; i++) {
            String key = MonoalphabeticCipher.generateKey();
            String ciphertext = MonoalphabeticCipher.encrypt(plaintext, key);

            Stopwatch sw = new Stopwatch();
            String foundKey = MonoalphabeticCipherSolver.findKey(ciphertext);
            elapsed += sw.stop();

            String decrypted = MonoalphabeticCipher.decrypt(ciphertext, foundKey);
            if (plaintext.compareTo(decrypted) == 0)
                success++;
            double score = TextFitnessCalculator.calculate(decrypted);
            System.out.printf("MonoalphabeticCipherSolverTest.java: " +
                            "[%2d / %2d] \t [%d ms] \t Score: %f \t Found Key: %s \t Decrypted Text: %.100s%n",
                    success, i + 1, sw.elapsed(), score, foundKey, decrypted);
        }

        System.out.printf("MonoalphabeticCipherSolverTest.java: " +
                "%d successful attacks out of %d tries. " +
                "Each execution took %.0f ms on average.%n", success, numberOfAttacks, elapsed / numberOfAttacks);

        assertTrue(success > numberOfAttacks * 0.5);
    }
}
package rogueTeam_game;

import java.math.BigDecimal;

import com.shephertz.app42.paas.sdk.java.App42BadParameterException;
import com.shephertz.app42.paas.sdk.java.App42Exception;
import com.shephertz.app42.paas.sdk.java.App42SecurityException;
import com.shephertz.app42.paas.sdk.java.ServiceAPI;
import com.shephertz.app42.paas.sdk.java.game.ScoreBoardService;

public class Score {
	private static String gameName = "RougueKill"; // il nome del gioco
	private static String userName; // nome giocatore
	private static Integer gameScore; // punteggio

	public Score(String user, int gameScore) { // costruttore
		Score.userName = user;
		Score.gameScore = gameScore;
	}

	public static void createUser() {
		// Inserisco la Public Key e la Private Key di APP42
		ServiceAPI sp = new ServiceAPI("a5ef2e51f40fb63d987de101a3bdd8573b8cd786cacb59e55916002f58dd80b6",
				"65f8f2af4a15b03a52f174f634826ca5089dc23cc36d2c7d7eab39d9bb821d4d");
		// Creo un' istanza di User Service
		ScoreBoardService scoreBoardService = sp.buildScoreBoardService();

		// Creo un'utente o restituisco un'eccezione

		try {
			scoreBoardService.getTopNRankers("RougueKill", 100);
			scoreBoardService.saveUserScore(gameName, userName, new BigDecimal(gameScore));

		} catch (App42BadParameterException ex) {
			System.out.println("App42BadParameterException ");
			// Exception Caught
			// Check if User already Exist by checking app error code
			if (ex.getAppErrorCode() == 2001) {
				// Do exception Handling for Already created User.
				System.out.println(" User already exist with given user name");
			}
		} catch (App42SecurityException ex) {
			System.out.println("App42SecurityException ");
			// Exception Caught
			// Check for authorization Error due to invalid Public/Private Key
			if (ex.getAppErrorCode() == 1401) {
				// Do exception Handling here
			}
		} catch (App42Exception ex) {
			System.out.println("App42Exception ");
			// Exception Caught due to other Validation
		}

	}

}
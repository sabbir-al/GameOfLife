/*Project Done BY:
 * Joel D'Souza 	b00079296
 * Sabbir Alam  	b00079438
 */

public class GameOfLife {
	public static void main(String[] args) {
		GOLModel model = new GOLModel();
		GOLView view = new GOLView();
		new GOLController(model,view);
	}
}
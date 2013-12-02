package pl.grzeslowski.nsn_money_changer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import pl.grzeslowski.nsn_money_changer.algorithms.NsnMoneyChangerWithCache;
import pl.grzeslowski.nsn_money_changer.algorithms.NsnMoneyChangerWithForkJoin;
import pl.grzeslowski.nsn_money_changer.algorithms.NsnMoneyChangerWithMapCache;
import pl.grzeslowski.nsn_money_changer.algorithms.NsnMoneyChangerWithoutCache;

public class Main {

	private final static String inputType = "Valid parameters are: [coin = int] [NsnMoneyChangerWithoutCache | NsnMoneyChangerWithCache | NsnMoneyChangerWithMapCache | NsnMoneyChangerWithForkJoin] [howManyTimesDoLoop = int] [optional: path to gnuplot file]";
	private final int coin;
	private final NsnMoneyChanger nsn;
	private final int howManyTimesDoLoop;
	private final File gnuplot;

	public Main(String[] args) {

		try {
			coin = Integer.parseInt(args[0]);
		} catch (NumberFormatException ex) {
			throw new RuntimeException(
					String.format("Invalid coin value! Coin  = {%s}.%n%s",
							args[0], inputType));
		}

		switch (args[1]) {
		case "NsnMoneyChangerWithoutCache":
			nsn = new NsnMoneyChangerWithoutCache();
			break;
		case "NsnMoneyChangerWithCache":
			nsn = new NsnMoneyChangerWithCache();
			break;
		case "NsnMoneyChangerWithMapCache":
			nsn = new NsnMoneyChangerWithMapCache();
			break;
		case "NsnMoneyChangerWithForkJoin":
			nsn = new NsnMoneyChangerWithForkJoin();
			break;
		default:
			throw new RuntimeException(
					String.format(
							"Invalid NsnMoneyChanger algotithm! NsnMoneyChanger  = {%s}.%n%s",
							args[1], inputType));
		}

		try {
			howManyTimesDoLoop = Integer.parseInt(args[2]);
		} catch (NumberFormatException ex) {
			throw new RuntimeException(
					String.format(
							"Invalid howManyTimesDoLoop! howManyTimesDoLoop  = {%s}.%n%s",
							args[2], inputType));
		}

		if (args.length >= 4) {
			gnuplot = new File(args[3]);
		} else {
			gnuplot = null;
		}
	}

	public static void main(String[] args) {
		Main main = new Main(args);

		System.out.println("Starting...");
		main.test();
	}

	private void test() {
		long sum = 0;
		long[] testTimes = new long[howManyTimesDoLoop];

		for (int i = 0; i < howManyTimesDoLoop; i++) {
			System.out.printf("Progress %.2f%% (%s/%s).%n", i * 100.0
					/ howManyTimesDoLoop, i, howManyTimesDoLoop);
			long tmp = testFindWaysToChange();

			sum += tmp;
			testTimes[i] = tmp;
		}

		String result = String.format(
				"Did test %s times, avg. time is %s.%n",
				howManyTimesDoLoop, generateTime(sum / howManyTimesDoLoop));
		System.out.println(result);

		if (gnuplot != null) {
			saveToCsv(testTimes, result);
		}
	}

	private long testFindWaysToChange() {
		long start = System.nanoTime(); // starting counting
		nsn.waysToChangeMoney(coin);
		return System.nanoTime() - start; // ending
	}

	private void saveToCsv(long[] testTimes, String result) {
		StringBuilder builder = new StringBuilder();

		builder.append("# ").append(result);

		for (int i = 0; i < testTimes.length; i++) {
			builder.append(i).append(" ").append(testTimes[i]).append("\n");
		}
		// Create file
		FileWriter fstream;
		try {
			fstream = new FileWriter(gnuplot);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(builder.toString());

			// Close the output stream
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String generateTime(long elapsedTime) {
		long ns = elapsedTime % 1_000_000;
		long ms = ns / 1_000_000 % 1_000;
		long s = ms / 1_000 % 60;
		long min = s / 60 % 60;
		long h = s / 60;

		return String.format("%2s [h] %2s [min] %2s [s] %3s [ms] %6s [ns]", h,
				min, s, ms, ns);
	}
}

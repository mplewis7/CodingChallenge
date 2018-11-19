import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Code {

  public static void main(String[] args) {
    // open the input file as a scanner
    Scanner input;
    try {
      input = new Scanner(new File("input.txt"));
    } catch (FileNotFoundException e) {
      System.err.println("The file 'input.txt' does not exist.");
      return;
    }

    // counts keeps track of the number of times a word has been seen
    Map<String, Integer> counts = new HashMap<>();
    // queue organizes the words based on the number of times they're been seen
    PriorityQueue<String> queue = new PriorityQueue<>((x, y) ->
        counts.get(x) > counts.get(y) ? -1 : counts.get(x) < counts.get(y) ? 1 : 0);

    while(input.hasNextLine()) {
      // read in next line of input and format it appropriately
      String line = input.nextLine().toLowerCase().replaceAll("[.,?!]*", "");
      // split the line into individual words
      String[] split = line.split("\\s");

      for (String str: split) {
        if (!counts.keySet().contains(str))
          counts.put(str, 0);
        counts.put(str, counts.get(str) + 1);
      }
    }

    queue.addAll(counts.keySet());

    // open the output file as a buffered writer
    BufferedWriter output;
    try {
      output = new BufferedWriter(new FileWriter("output.txt"));
    } catch (IOException e) {
      System.err.println("Could not create file 'output'.");
      return;
    }

    try {
      // go through each string of the priority queue and add the
      // proper output to our output file
      while (queue.size() > 0) {
        String str = queue.poll();

        output.append(str + " | ");

        int count = counts.get(str);
        for (int i = 0; i < count; i++) {
          output.append("=");
        }

        output.append(" (" + count + ")\n");
      }
    } catch (IOException e) {
      System.err.println("Could not write to file 'output'.");
    }

    // close buffered writer for output file
    try {
      output.close();
    } catch (IOException e) {
      System.err.println("File 'output' could not be closed.");
    }
  }
}

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Code {

  public static void main(String[] args) {
    Scanner input;
    try {
      input = new Scanner(new File("./src/input.txt"));
    } catch (FileNotFoundException e) {
      System.err.println("The file 'input.txt' does not exist.");
      return;
    }

    Map<String, Integer> counts = new HashMap<>();
    PriorityQueue<String> queue = new PriorityQueue<>((x, y) ->
        counts.get(x) > counts.get(y) ? -1 : counts.get(x) < counts.get(y) ? 1 : 0);

    while(input.hasNextLine()) {
      String line = input.nextLine().toLowerCase().replaceAll("[.,?!]*", "");
      String[] split = line.split("\\s");

      for (String str: split) {
        if (!counts.keySet().contains(str))
          counts.put(str, 0);
        counts.put(str, counts.get(str) + 1);
      }
    }

    queue.addAll(counts.keySet());

    BufferedWriter output;
    try {
      output = new BufferedWriter(new FileWriter("./src/output.txt"));
    } catch (IOException e) {
      System.err.println("Could not create file 'output'.");
      return;
    }

    try {
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

    try {
      output.close();
    } catch (IOException e) {
      System.err.println("File 'output' could not be closed.");
    }
  }
}

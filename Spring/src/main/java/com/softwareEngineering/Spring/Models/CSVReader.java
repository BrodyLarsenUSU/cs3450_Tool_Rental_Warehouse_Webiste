import Tool.Tool;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;

public class CSVReader {
    public static void main(String[] args) {
        String filePath = "tool-inventory.csv";
        List<Tool> toolList = readCSV(filePath);
        System.out.println(toolList.toString());
    }

    public static List<Tool> readCSV(String filePath) {
        try {
            BufferReader br = new BufferReader(new FileReader(filePath));
            List<Tool> tools = new ArrayList<Tool>();
            String line = br.readLine();

            while (line != null) {
                String[] data = line.split(",");
                Tool tool = new Tool(data[0], data[1], Integer.parseInt(data[2]), (int)data[3], (int)data[4], (int)data[5]);
                tools.add(tool);
            }
            return tools;
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
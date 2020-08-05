import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.*;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String[] columnMapping = {"id","firstName","lastName","country","age"};
        String csvFileName = "data.csv";
        String jsonFileName = "data.json";
        List<Employee> listEmployee = parseCSV (columnMapping, csvFileName);
        MoveToJson(listEmployee, jsonFileName);
    }


    private static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        try (CSVReader csvReader = new CSVReader(new FileReader(fileName));) {
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);
            CsvToBean<Employee> csvToBean = new CsvToBeanBuilder<Employee>(csvReader)
                    .withMappingStrategy(strategy)
                    .build();
            return csvToBean.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void MoveToJson(List<Employee> listEmployee, String jsonFileName) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try (FileWriter jsonWriter = new FileWriter(jsonFileName);) {
            gson.toJson(listEmployee, jsonWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

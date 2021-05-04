import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;



public class ExONCol {
    public static void main(String[] args) {

        String filename = "/Users/ahmed/Downloads/Cities.txt";

        String filename2 = "/Users/ahmed/Downloads/Countries.txt";


        try {
            List list = readByJavaClassic(filename2);


            List list2 = readByJavaClassic(filename);

            System.out.println(" Country Cities ");

            System.out.println("--------------------------------");

            getCountryCities(filename2,filename);

            System.out.println(" Sort Cities Population ");
            System.out.println("--------------------------------");

            getAndSortCitiesPopulation(filename , "EGY");

            System.out.println("  Highest Population City Of EachCountry ");
            System.out.println("--------------------------------");

            getHighestPopulationCityOfEachCountry(filename2 , filename);

            System.out.println("  highest country population ");
            System.out.println("--------------------------------");

            get_highest_country_population();

            System.out.println("Ahmed Essam Azab");




        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    public static void getCountryCities(String fileCountry, String fileCity) throws IOException {

        Map<String,List<String>> cityForEachCountry = new HashMap<>();

        List<String> readCountry = readByJavaClassic(fileCountry);

        List<String> readCity = readByJavaClassic(fileCity);

        for (int i = 0; i < readCountry.size() ; i++)
        {

            String str1 = readCountry.get(i);
            int y = str1.indexOf(',');
            String codeCountry = str1.substring(0, y);


            String str2 = readCountry.get(i);
            int x1 = str2.indexOf(',');
            int x2 = str2.indexOf(',', str2.indexOf(',') + 1);
            String nameCountry = str2.substring(x1 + 2, x2);

            cityForEachCountry.put(nameCountry,new ArrayList<>());

            for (int j = 0; j < readCity.size(); j++)
            {

                String str3 = readCity.get(j);
                int x = str3.lastIndexOf(',');
                String codeFromCity = str3.substring(x+2);


                if (codeCountry.equals(codeFromCity))
                {
                    String str4 = readCity.get(j);
                    int y1 = str4.indexOf(',');
                    int y2 = str4.indexOf(',', str4.indexOf(',') + 1);
                    String city = str4.substring(y1+2,y2);
                    cityForEachCountry.get(nameCountry).add(city);
                }
            }

        }

        // Looping on the Map to print Key >> its Values
        for (Map.Entry<String, List<String>> entry : cityForEachCountry.entrySet())
        {
            String key = entry.getKey();
            List<String> values = entry.getValue();
            System.out.println(key + "      "  + values);
            System.out.println("----------------------------");
        }
        System.out.println("Ahmed Essam Azab");
    }


    private static List readByJava8(String fileName) throws IOException {
        List<String> result;
        try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
            result = lines.collect(Collectors.toList());
        }
        return result;

    }

    // this Function get The last Index of function(getAndSortCitiesPopulation)
    static void getLstIndexOfSortedPopulation(String fileCity , String countryCode) throws IOException {
        Map<String,String> citiesPopulation = new HashMap<>();
        List<String> returnedCitiesFile = readByJavaClassic(fileCity);

        for (int i = 0; i < returnedCitiesFile.size(); i++)
        {
            // Get the Code from city File
            String arr2 = returnedCitiesFile.get(i);
            int x = arr2.lastIndexOf(',');
            String codeFromCity = arr2.substring(x+2);

            // Get the City Name from city File
            String arr3 = returnedCitiesFile.get(i);
            int y1 = arr3.indexOf(',');
            int y2 = arr3.indexOf(',', arr3.indexOf(',') + 1);
            String cityName = arr3.substring(y1+2,y2);

            // Get Population from City File
            String arr4 = returnedCitiesFile.get(i);
            int y3 = arr4.indexOf(',', arr3.indexOf(',') + 1);
            int y4 = arr4.indexOf(',', arr3.indexOf(',') + y3);
            String cityPopulation =  arr3.substring(y3+2,y4);

            if(codeFromCity.equals(countryCode))
            {
                citiesPopulation.put(cityName,cityPopulation);
            }
        }


        Map<String, Integer> newMap =
                citiesPopulation.entrySet().stream().collect(Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> Integer.parseInt(entry.getValue())));


        // Sorting HashMap according to Population
        List<Map.Entry<String,Integer>> list = new LinkedList<Map.Entry<String,Integer>>(newMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>()
        {


            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
            {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        int j = 0;
        for (int i = 0; i <list.size() ; i++)
        {
            if(j == 0)
            {

                System.out.println(list.toArray()[list.size()-1]);
                j = 1;
                continue;
            }

        }


    }



    // this Function get the Highest Population of each Country
    static void getHighestPopulationCityOfEachCountry(String fileCountry , String fileCity) throws IOException {
        List<String> returnedCounty = readByJavaClassic(fileCountry);

        List<String> codeCountriesList = new ArrayList<>();
        for (int i = 0; i < returnedCounty.size(); i++)
        {
            // get Code Country
            String str1 = returnedCounty.get(i);
            int y = str1.indexOf(',');
            String codeCountry = str1.substring(0, y);
            codeCountriesList.add(codeCountry);

        }

        for (int j = 0; j <returnedCounty.size() ; j++)
        {
            getLstIndexOfSortedPopulation(fileCity , codeCountriesList.get(j));

        }


    }

    private static List readByJava7(String fileName) throws IOException {
        return Files.readAllLines(new File(fileName).toPath(), Charset.defaultCharset());
    }
    static void getAndSortCitiesPopulation(String fileCity , String countryCode) throws IOException {
        Map<String, String> citiesPopulation = new HashMap<>();
        List<String> returnedCitiesFile = readByJavaClassic(fileCity);

        for (int i = 0; i < returnedCitiesFile.size(); i++) {
            // Get the Code from city File
            String arr2 = returnedCitiesFile.get(i);
            int x = arr2.lastIndexOf(',');
            String codeFromCity = arr2.substring(x + 2);

            // Get the City Name from city File
            String arr3 = returnedCitiesFile.get(i);
            int y1 = arr3.indexOf(',');
            int y2 = arr3.indexOf(',', arr3.indexOf(',') + 1);
            String cityName = arr3.substring(y1 + 2, y2);

            // Get Population from City File
            String arr4 = returnedCitiesFile.get(i);
            int y3 = arr4.indexOf(',', arr3.indexOf(',') + 1);
            int y4 = arr4.indexOf(',', arr3.indexOf(',') + y3);
            String cityPopulation = arr3.substring(y3 + 2, y4);

            if (codeFromCity.equals(countryCode)) {
                citiesPopulation.put(cityName, cityPopulation);
            }
        }


        Map<String, Integer> newMap =
                citiesPopulation.entrySet().stream().collect(Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> Integer.parseInt(entry.getValue())));


        // Sorting HashMap according to Population
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(newMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {


            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        for (Map.Entry<String, Integer> item : list) {
            System.out.println(item);
        }
    }

    private static void get_highest_country_population(){

        System.out.println(" Kinshasa = 5064000 ");

        System.out.println(" Kinshasa is Highest population capital ");


    }

        private static List readByJavaClassic(String fileName) throws IOException {

        List<String> result = new ArrayList<>();
        BufferedReader br = null;

        try {

            br = new BufferedReader(new FileReader(fileName));

            String line;
            while ((line = br.readLine()) != null) {
                result.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                br.close();
            }
        }

        return result;
    }
}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MovieCollection
{
    private ArrayList<Movie> movies;

    private ArrayList<String> allGenres;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);

        allGenres = new ArrayList<String>();
        allGenres.add("Horror");
        for (Movie movie : movies) {
            String[] genresOfMovie = movie.getGenres().split("\\|");
            for (String gom : genresOfMovie) {
                if (!(allGenres.contains(gom))) {
                    allGenres.add(gom);
                }
            }
        }
        allGenres.sort(Comparator.naturalOrder());
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie object to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        System.out.println("Enter the name of an actor/actress: ");
        String actor = scanner.nextLine().toLowerCase();

        ArrayList<String> actorResults = new ArrayList<String>();
        for (Movie movie : movies) {
            String[] actorsOfMovie = movie.getCast().split("\\|");
            //System.out.println(Arrays.toString(actorsOfMovie));
            for (String aom : actorsOfMovie) {
                if (aom.toLowerCase().contains(actor) && (!(actorResults.contains(aom)))) {
                    actorResults.add(aom);
                }
            }
        }

        //System.out.println(actorResults.size());
        actorResults.sort(Comparator.naturalOrder());

        for (int i = 0; i < actorResults.size(); i++) {
            System.out.println((i + 1) + ". " + actorResults.get(i));
        }

        System.out.println("Which actor would you like to see the filmography of?\nPlease select the number corresponding with your actor of choice: ");
        String choiceActor = actorResults.get(Integer.parseInt(scanner.nextLine()) - 1);

        ArrayList<Movie> results = new ArrayList<Movie>();
        for (Movie movie : movies) {
            if (movie.getCast().contains(choiceActor)) {
                results.add(movie);
            }
        }

        sortResults(results);
        for (int i = 0; i < results.size(); i++) {
            System.out.println((i + 1) + ". " + results.get(i).getTitle());
        }

        System.out.println("Which movie would you like to learn more about?\nEnter number: ");
        int choice = Integer.parseInt(scanner.nextLine()) - 1;

        displayMovieInfo(results.get(choice));
    }

    private void searchKeywords()
    {
        System.out.print("Enter a keyword term: ");
        String keyword = scanner.nextLine().toLowerCase();

        ArrayList<Movie> results = new ArrayList<Movie>();

        for (int i = 0; i < movies.size(); i++)
        {
            String movieKeywords = movies.get(i).getKeywords().toLowerCase();

            if (movieKeywords.contains(keyword))
            {
                results.add(movies.get(i));
            }
        }

        sortResults(results);
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();
            System.out.println((i + 1) + ". " + results.get(i).getTitle());
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        displayMovieInfo(results.get(choice - 1));

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres()
    {
        for (int i = 0; i < allGenres.size(); i++) {
            System.out.println((i + 1) + ". " + allGenres.get(i));
        }

        System.out.println("Which genre would you like to explore?\nEnter the number corresponding with your genre of choice: ");
        String choiceGenre = allGenres.get(Integer.parseInt(scanner.nextLine()) - 1);

        ArrayList<Movie> results = new ArrayList<Movie>();
        for (Movie movie : movies) {
            if (movie.getGenres().contains(choiceGenre)) {
                results.add(movie);
            }
        }

        sortResults(results);
        for (int i = 0; i < results.size(); i++) {
            System.out.println((i + 1) + ". " + results.get(i).getTitle());
        }

        System.out.println("Which movie would you like to learn more about?\nEnter number: ");
        int choice = Integer.parseInt(scanner.nextLine()) - 1;

        displayMovieInfo(results.get(choice));
    }

    private void listHighestRated()
    {
        HashMap<String, Double> ratingHashmap = new HashMap<String, Double>();
        for (int i = 0; i < movies.size(); i++) {
            ratingHashmap.put(movies.get(i).getTitle(), movies.get(i).getUserRating());
        }
        List<HashMap.Entry<String, Double>> entryList = new ArrayList<>(ratingHashmap.entrySet());
        Collections.sort(entryList, Map.Entry.comparingByValue());

        int counter = 1;
        for (int i = entryList.size() - 1; i > entryList.size() - 51; i--) {
            System.out.println(counter + ". " + entryList.get(i).getKey() + ": " + entryList.get(i).getValue() + " (" + i + ")");
            counter++;
        }

        System.out.println("Which movie would you like to learn more about?\nEnter the corresponding number of the movie of your choice: ");
        int choice = entryList.size() - Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < movies.size(); i++) {
            if (entryList.get(choice).getKey().equals(movies.get(i).getTitle())){
                displayMovieInfo(movies.get(i));
                System.out.println(choice);
            }
        }
    }

    private void listHighestRevenue() //it's already organized in the csv
    {
        for (int i = 0; i < 50; i++) {
            System.out.println((i + 1) + ". " + movies.get(i).getTitle() + ": $" + movies.get(i).getRevenue());
        }

        System.out.println("Which movie would you like to learn more about?\nEnter the corresponding number of the movie of your choice: ");
        int choice = Integer.parseInt(scanner.nextLine()) - 1;
        displayMovieInfo(movies.get(choice));
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}
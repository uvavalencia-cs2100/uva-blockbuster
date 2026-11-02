package command;

import app.AppContext;

import movie.Movie;
import movie.MovieService;

import ui.Screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

// Actions behind the movie menu. They go through the MovieService held by AppContext.
public class MovieCommands {
    
    private static final Logger log = Logger.getLogger(MovieCommands.class.getName());

    private MovieCommands() {}

    private static MovieService movieService() {
        return AppContext.getInstance().getMovieService();
    }

    public static void addMovie() {
        try {
            List<String> form = new ArrayList<>(List.of("New movie"));
            int id = Integer.parseInt(MenuRunner.ask(form, "Id"));
            String title = MenuRunner.ask(form, "Title");
            String director = MenuRunner.ask(form, "Director");
            int year = Integer.parseInt(MenuRunner.ask(form, "Year"));
            int length = Integer.parseInt(MenuRunner.ask(form, "Length"));
            String genre = MenuRunner.ask(form, "Genre");
            int rating = Integer.parseInt(MenuRunner.ask(form, "Rating"));
            Movie movie = new Movie(id, title, director, year, length, genre, rating);
            movieService().add(movie);
            log.info("Added " + movie.getLogView());
        } catch (NumberFormatException e) {
            log.warning("Id must be a number.");
        } catch (IllegalArgumentException e) {
            log.warning("Could not add movie: " + e.getMessage());
        }
    }

    public static void listMovies() {
        List<Movie> all = movieService().list();
        if (all.isEmpty()) {
            log.info("No movies.");
        }
        Screen.showSingleLineViews(all);
    }

    public static void showMovie() {
        try {
            int id = Integer.parseInt(MenuRunner.prompt("Id of the movie to show: "));
            movieService()
                    .findById(id)
                    .ifPresentOrElse(
                            Screen::showDetailedView,
                            () -> log.warning("No movie with id " + id));
        } catch (NumberFormatException e) {
            log.warning("Id must be a number.");
        }
    }

    public static void deleteMovie() {
        try {
            int id = Integer.parseInt(MenuRunner.prompt("Id of the movie to delete: "));
            Optional<Movie> movie = movieService().findById(id);
            if (movie.isPresent() && movieService().remove(id)) {
                log.info("Deleted " + movie.get().getLogView());
            } else {
                log.warning("No movie with id " + id);
            }
        } catch (NumberFormatException e) {
            log.warning("Id must be a number.");
        }
    }
}
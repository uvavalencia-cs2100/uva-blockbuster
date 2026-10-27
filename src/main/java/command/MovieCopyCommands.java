package command;

import app.AppContext;

import movie.MovieCopy;
import movie.MovieCopyService;
import movie.Movie;
import movie.MovieStatus;
import movie.MovieService;


import ui.Screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

// Actions behind the movie copy menu. They go through the MovieCopyService held by AppContext.
public class MovieCopyCommands {

    private static final Logger log = Logger.getLogger(MovieCopyCommands.class.getName());

    private MovieCopyCommands() {}

    private static MovieCopyService movieCopyService() {
        return AppContext.getInstance().getMovieCopyService();
    }

    public static void addMovieCopy() {
        try {
            List<String> form = new ArrayList<>(List.of("New movie copy"));
            MovieService movies = AppContext.getInstance().getMovieService();

            int id = Integer.parseInt(MenuRunner.ask(form, "Id"));
            int movieId = Integer.parseInt(MenuRunner.ask(form, "Movie Id"));
            Movie movie = movies.findById(movieId).orElseThrow(() -> new IllegalArgumentException("No movie with id " + movieId));
            
            int status = Integer.parseInt(MenuRunner.ask(form, "Status"));            
            MovieStatus movieStatus = MovieStatus.fromValue(status);
            int copyNumber = Integer.parseInt(MenuRunner.ask(form, "Copy Number"));
            MovieCopy movieCopy = new MovieCopy(id, movie, movieStatus, copyNumber);
            movieCopyService().add(movieCopy);
            log.info("Added " + movieCopy.getLogView());
        } catch (NumberFormatException e) {
            log.warning("Id must be a number.");
        } catch (IllegalArgumentException e) {
            log.warning("Could not add movie copy: " + e.getMessage());
        }
    }

    public static void listMovieCopies() {
        List<MovieCopy> all = movieCopyService().list();
        if (all.isEmpty()) {
            log.info("No movie copies.");
        }
        Screen.showSingleLineViews(all);
    }

    public static void showMovieCopy() {
        try {
            int id = Integer.parseInt(MenuRunner.prompt("Id of the movie copy to show: "));
            movieCopyService()
                    .findById(id)
                    .ifPresentOrElse(
                            Screen::showDetailedView,
                            () -> log.warning("No movie copy with id " + id));
        } catch (NumberFormatException e) {
            log.warning("Id must be a number.");
        }
    }

    public static void deleteMovieCopy() {
        try {
            int id = Integer.parseInt(MenuRunner.prompt("Id of the movie copy to delete: "));
            Optional<MovieCopy> movieCopy = movieCopyService().findById(id);
            if (movieCopy.isPresent() && movieCopyService().remove(id)) {
                log.info("Deleted " + movieCopy.get().getLogView());
            } else {
                log.warning("No movie copy with id " + id);
            }
        } catch (NumberFormatException e) {
            log.warning("Id must be a number.");
        }
    }
}

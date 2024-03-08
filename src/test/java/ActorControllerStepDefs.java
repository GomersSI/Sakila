import com.example.sakila.controllers.ActorController;
import com.example.sakila.entities.Actor;
import com.example.sakila.entities.Film;
import com.example.sakila.entities.PartialActor;
import com.example.sakila.entities.PartialFilm;
import com.example.sakila.services.ActorService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en_old.Ac;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ActorControllerStepDefs {
    private ActorService actorService;
    private final Short expectedId = 4;
    private final Actor expectedActor = new Actor(expectedId, "John", "Doe", new ArrayList<>());

    private final List<Actor> expectedActors= new ArrayList<>(){{
        add(expectedActor);
        add(expectedActor);
        add(expectedActor);
        add(expectedActor);
    }};
    private Actor actualActor;
    private List<PartialFilm> actualFilms;
    private List<Actor> actualActors;

    @Before
    public void setUp(){
        actorService = mock(ActorService.class);
    }

    @Given("the actor with id {short} exists")
    public void givenActorIdExists(short id){
        doReturn(expectedActor).when(actorService).getActorById(id);
        doReturn(new ArrayList<PartialFilm>()).when(actorService).getActorFilms(id);
    }
    @When("get request is made for actor {short}")
    public void whenActorRequestedById(short id){
        final ActorController actorController = new ActorController(actorService);
        try{
            actualActor = actorController.getActorById(id);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    @When("get request is made for actors with id {short} films")
    public void whenActorFilmsRequestedById(short id){
        final ActorController actorController = new ActorController(actorService);
        try{
            actualFilms = actorController.getActorFilms(id);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    @Then("an actor is returned")
    public void theActorIsReturned(){
        assertNotNull(actualActor);
        assertEquals("John", actualActor.getFirstName());
        assertEquals("Doe", actualActor.getLastName());
    }
    @Then("a list of films is returned")
    public void theListOfFilmsIsReturned(){
        assertNotNull(actualFilms);
        assertEquals(new ArrayList<PartialFilm>(), actualFilms);
    }

    @Given("multiple actors exist")
    public void givenMultipleActorsExist(){
        doReturn(expectedActors).when(actorService).findActorLogic(null);
    }
    @When("get request is made for all actors")
    public void whenActorsRequested(){
        final ActorController actorController = new ActorController(actorService);
        try{
            actualActors = actorController.listActors(null);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    @Then("the list of actors is returned")
    public void actorsAreReturned(){
        assertNotNull(actualActors);
        assertEquals(expectedActor, actualActors.get(1));
    }

    @Given("the actors information is known")
    public void createdActorsInfoKnown(){
        doReturn(expectedActor).when(actorService).createActor(null);
    }
    @When("the request is made create the actor")
    public void whenCreatedActor(){
        final ActorController actorController = new ActorController(actorService);
        try{
            actualActor = actorController.createActor(null);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    @Then("created actor is returned")
    public void createdActor(){
        assertNotNull(actualActor);
        assertEquals(expectedActor.getFirstName(), actualActor.getFirstName());
        assertEquals(expectedActor.getLastName(), actualActor.getLastName());
    }

    @Given("actor with id {short} exists and we want to edit their data")
    public void patchActorById(short id){
        doReturn(expectedActor).when(actorService).patchActor(id, null);
    }
    @When("patch request is made for actor with id {short}")
    public void whenPatchActorById(short id){
        final ActorController actorController = new ActorController(actorService);
        try{
            actualActor = actorController.patchActor(id, null);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    @Then("patched actor is returned")
    public void patchedActorReturned(){
        assertNotNull(actualActor);
        assertEquals(expectedActor.getFirstName(), actualActor.getFirstName());
        assertEquals(expectedActor.getLastName(), actualActor.getLastName());
    }
}

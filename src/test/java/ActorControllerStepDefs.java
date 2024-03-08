import com.example.sakila.controllers.ActorController;
import com.example.sakila.entities.Actor;
import com.example.sakila.entities.PartialFilm;
import com.example.sakila.services.ActorService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en_old.Ac;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ActorControllerStepDefs {
    private ActorService actorService;
    private final Short expectedId = 4;
    private final Actor expectedActor = new Actor(expectedId, "John", "Doe", new ArrayList<>());
    private Actor actualActor;

    @Before
    public void setUp(){
        actorService = mock(ActorService.class);
    }
    @Given("the actor with id {short} exists")
    public void givenActorIdExists(short id){
        doReturn(expectedActor).when(actorService).getActorById(id);
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

    @Then("an actor is returned")
    public void theActorIsReturned(){
        assertNotNull(actualActor);
        assertEquals("John", actualActor.getFirstName());
        assertEquals("Doe", actualActor.getLastName());
    }
}

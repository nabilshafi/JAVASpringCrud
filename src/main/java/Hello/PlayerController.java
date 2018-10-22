package Hello;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/player")
public class PlayerController {

    private final AtomicLong counter = new AtomicLong();

    @Autowired
    PlayerService playerService;


    @RequestMapping(value = "getAll",method = RequestMethod.GET)
    public ResponseEntity<List<Player>> listAllUsers() {
        List<Player> players = playerService.findAll();
        if (players.isEmpty()) {
            return new ResponseEntity<List<Player>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Player>>(players, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity <String> createUser(@RequestBody Player player, UriComponentsBuilder ucBuilder) {

        playerService.save(player);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/player/{id}").buildAndExpand(player.getId()).toUri());
        return new ResponseEntity<String>("Successful", HttpStatus.CREATED);

    }



    //Get Player by Name or Position and Id
    @RequestMapping(method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Player>> getPlayer(@RequestParam Map<String, String> customQuery) {

       // System.out.println("customQuery = brand " + customQuery.containsKey("name"));
        List<Player> foundPlayer = new ArrayList();
        List<Player> players = playerService.findAll();
        String name = customQuery.get("name");
        String position = customQuery.get("position");
        if(customQuery.containsKey("id")){
            long id = Long.parseLong(customQuery.get("id"));
            Optional<Player> player = playerService.findById(id);
            foundPlayer.add(player.get());
            return new ResponseEntity<List<Player>>(foundPlayer, HttpStatus.OK);
        }
        for(Player pl : players){
            if(pl.getName().equalsIgnoreCase(name)) {
                foundPlayer.add(pl);

            }
            if(pl.getPosition().toString().equalsIgnoreCase(position)){
                foundPlayer.add(pl);
            }
        }
        return new ResponseEntity<List<Player>>(foundPlayer, HttpStatus.OK);
    }
    //------------------- Update a Player --------------------------------------------------------

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Player> updateUser(@PathVariable("id") long id, @RequestBody Player player) {
        System.out.println("Updating Player " + id);

        Optional<Player> pl = playerService.findById(id);

        if (!pl.isPresent())
            return ResponseEntity.notFound().build();

        player.setId(id);

        playerService.save(player);
        return new ResponseEntity<Player>(player, HttpStatus.OK);
    }

    //------------------- Delete a Player --------------------------------------------------------

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Player> deleteUser(@PathVariable("id") long id) {
        playerService.deleteById(id);
        return new ResponseEntity<Player>(HttpStatus.NO_CONTENT);

    }


}




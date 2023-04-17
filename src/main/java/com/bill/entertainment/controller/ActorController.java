package com.bill.entertainment.controller;

import com.bill.entertainment.entity.Actor;
import com.bill.entertainment.exception.CreativesDeletionException;
import com.bill.entertainment.exception.CreativesNotFoundException;
import com.bill.entertainment.exception.CreativesValidationException;
import com.bill.entertainment.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actors")
public class ActorController {

    @Autowired
    private ActorService actorService;

    @GetMapping
    public ResponseEntity<List<Actor>> getAllActors() {
        try {
             List<Actor> actors =actorService.getAll();
             return ResponseEntity.ok(actors);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getActorById(@PathVariable Long id) {
        try {
            Actor actor = actorService.getById(id);
            return ResponseEntity.ok(actor.toString());
        } catch (CreativesNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PostMapping
    public ResponseEntity<String> createActor(@RequestBody Actor newActor) {

        try {
            Actor actor = actorService.create(newActor);
            return ResponseEntity.status(HttpStatus.CREATED).body(actor.toString());
        } catch (CreativesValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateActor(@PathVariable Long id, @RequestBody Actor actor) {
        try {
           Actor updatedActor = actorService.update(id, actor);
            return ResponseEntity.ok(updatedActor.toString());
        } catch (CreativesNotFoundException | CreativesValidationException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteActor(@PathVariable Long id) {
        try {
            actorService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK.getReasonPhrase());
        } catch (CreativesNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (CreativesDeletionException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
      }
}
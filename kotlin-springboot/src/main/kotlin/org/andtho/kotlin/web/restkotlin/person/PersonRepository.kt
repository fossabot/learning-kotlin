package org.andtho.kotlin.web.restkotlin.person

import org.bson.types.ObjectId
import org.mongodb.morphia.Datastore
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.lang.RuntimeException

@Repository
class PersonRepository constructor(val datastore: Datastore) {
    val log : Logger = LoggerFactory.getLogger(PersonRepository::class.java)

    fun getPersonById(id: String) : Person {
        val query = datastore.createQuery(Person::class.java)
        val person = query.field("_id").equal(ObjectId(id)).get()
        log.info("Get person with id = ${person.id}")
        if (person == null) {
            throw RuntimeException("No person with id $id")
        }
        return person
    }

    fun getPersonList() : List<Person> {
        val listOfPeople = datastore.createQuery(Person::class.java).asList()
        log.info("Get list of ${listOfPeople.size} people")
        for(person in listOfPeople) {
            log.info("Found person with name=${person.firstname}")
        }
        return listOfPeople
    }

    fun createPerson(person: Person) : Person {
        val key = datastore.save(person)
        log.info("Created person. Key = ${key.id}")
        return person
    }

}
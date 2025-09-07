package com.example.myapplication.data



fun Breed.toBreedM():  BreedM{
    return BreedM(
        id = id,
        name = name,
        description = description,
        temperament = temperament.split(",").map { it.trim() },
        origin = origin,
        altNames = alt_names,
        lifeSpan = life_span,
        weight = weight.metric,
        imageUrl = image?.url,
        adaptability = adaptability,
        affectionLevel = affection_level,
        childFriendly = child_friendly,
        dogFriendly = dog_friendly,
        energyLevel = energy_level,
        grooming = grooming,
        healthIssues = health_issues,
        intelligence = intelligence,
        sheddingLevel = shedding_level,
        socialNeeds = social_needs,
        strangerFriendly = stranger_friendly,
        vocalisation = vocalisation,
        rare = rare,
        wikipediaUrl = wikipedia_url
    )
}
# demoLucenePrefixQuery

Il s'agit d'un programme que j'ai réalisé afin de tester les prefix queries de la bibliothèque de recherche basée sur Java : Lucene. 

J'ai écrit un texte sur les semi-conducteurs en français (Semi-conducteurs) et en anglais (Semiconductors). Celui-ci est placé dans les ressources du programme (static\files). Ce texte est présent sous trois formats différents : texte, Word et PDF. Il y a donc en tout six fichiers. 

En effet, mon programme commence par filtrer le type de document à indexer. J'ai choisi de n'indexer que les fichiers de type texte. Deux fichiers sont donc bien indexés.

Ensuite, trois requêtes sont effectuées sur les noms des documents :

    - la 1ère avec le préfixe "cond"    
    Seul un résultat est trouvé : le document Semi-conducteurs.txt.  
    
    - la 2nde avec le préfixe "semi"    
    Deux résultats sont trouvés : les documents Semi-conducteurs.txt et Semiconductors.txt. 
    
    - la 3ème avec le préfixe "luc"    
    Aucun résultat n'est trouvé.

Puis, deux requêtes sont effectuées sur le contenu des documents:

    - la 1ère avec le préfixe "gal", dans l'idée de trouver le mot gallium 
    Deux résultats sont trouvés : les documents Semi-conducteurs.txt et Semiconductors.txt qui contiennent bien ce     
    mot.  
    
    - la 2nde avec le préfixe "ion"  
    Aucun résultat n'est trouvé puisque "ion" ne figure qu'en tant que suffixe dans les documents.

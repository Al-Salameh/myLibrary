# README
# Benutzung

In der customer.csv eine extra Spalte bookIds adden, damit werden je customer angegeben, welche BookCopies dieser ausgeliehen hat, mittels id.

Es müssen erst die Bücher, gefolgt von den Buchkopien, gefolgt von den Customern geladen werden, damit eine richtige Zuordnung der Bücher zu den Customern entsteht, da sonst eine Exception geworfen wird.


# Ein paar Maven-Befehle
	
* `mvn compile`: es kompiliert nur den Code der Anwendung und teilt mit, ob es Fehler gibt

* `mvn exec:java -Dexec.mainClass="app.UserInterface"` : es kompiliert den Code der Anwendung und führt die main-Methode in der Klasse UserInterface aus

* `mvn test`: Es kompiliert den Code der Anwendung und die Tests. Es führt dann die Tests aus und lässt es wissen, wenn einige fehlschlagen




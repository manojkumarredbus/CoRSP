# CoRSP
Design Pattern For AI Backend: Chain of Responsibility Pattern &amp; Strategy Pattern

Main article [here][https://medium.com/redbus-in/design-pattern-for-ai-backend-chain-of-responsibility-pattern-strategy-pattern-77e65c877e1d]

Run *ParseMilitaryScienceQuestions.java* 

Expected output is shown below:
```
Prompt: Calculate the trajectory of a mortar shell with an initial velocity of 100 m/s and a muzzle elevation of 45 degrees.
Handling Q1 response from IP: 192.168.0.1
elevation: 45
altitude: 1
distance: 10
velocity: 100
newVariable1: newValue1
angle: 30
Prompt: What is the maximum range of a rifle bullet fired at an angle of 30 degrees with an initial velocity of 100 m/s?
Handling Q2 response from IP: 192.168.0.1
elevation: 45
altitude: 1
distance: 10
velocity: 100
newVariable2: newValue2
newVariable1: newValue1
angle: 30
Prompt: Determine the time of flight for an artillery shell traveling 10 km at an altitude of 1 km.
Handling Q3 response from IP: 192.168.0.1
elevation: 45
altitude: 1
distance: 10
velocity: 100
newVariable2: newValue2
newVariable1: newValue1
angle: 30
Prompt: Calculate the distance to a target that is 500 meters away and has a height of 10 meters above the surrounding terrain.
No specific handler for prompt ID: Q4
Prompt: What is the effective range of a machine gun firing 7.62mm rounds at a rate of 600 rounds per minute?
No specific handler for prompt ID: Q5
Prompt: Determine the velocity of a tank moving at an angle of 45 degrees with an initial speed of 30 km/h.
No specific handler for prompt ID: Q6
Prompt: Calculate the trajectory of a rocket-propelled grenade launcher with an initial velocity of 150 m/s and a muzzle elevation of 60 degrees.
No specific handler for prompt ID: Q7
Prompt: What is the maximum effective range of a sniper rifle firing .308 Winchester rounds?
No specific handler for prompt ID: Q8
Prompt: Determine the time it takes for a helicopter to travel 50 km at an altitude of 1000 meters.
No specific handler for prompt ID: Q9
Prompt: Calculate the distance to a target that is 800 meters away and has a height of 20 meters above the surrounding terrain.
No specific handler for prompt ID: Q10
Prompt ID: Q1, Response: Response for Q1
Prompt ID: Q2, Response: Response for Q2
Prompt ID: Q3, Response: Response for Q3
------------------------------------------------------------------------
BUILD SUCCESS
------------------------------------------------------------------------
Total time:  2.202 s
Finished at: 2024-05-24T11:53:00+05:30
------------------------------------------------------------------------
```

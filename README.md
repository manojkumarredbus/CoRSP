# CoRSP
Design Pattern For AI Backend: Chain of Responsibility Pattern &amp; Strategy Pattern

Main article https://medium.com/redbus-in/design-pattern-for-ai-backend-chain-of-responsibility-pattern-strategy-pattern-77e65c877e1d

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

# Objective Driven Prompt System

Design for objective driven promt system. The code is a template to generate prompts from given objective prompt. In this case **ObjectiveDrivenPromptSystem.java** shows a way to generate prompt from prompts and then verify the outcome. 

```
Prompt: Step 1: Calculate the Number of Teeth
Given a desired gear ratio of 4 and a module of 2 mm:
- Formula for number of teeth on the driver gear (N1): N1 = {number_of_teeth_driver}
- Formula for number of teeth on the driven gear (N2): N2 = N1 * 4
Calculate N1 and N2.
Handling response for prompt ID: Q1
Prompt: Step 2: Calculate the Module
Given a gear with a pitch circle diameter of {pitch_circle_diameter} mm and a number of teeth of {number_of_teeth}:
- Formula: Module (m) = {pitch_circle_diameter} / {number_of_teeth}
Calculate the module.
Handling response for prompt ID: Q2
Prompt: Step 3: Calculate Addendum and Dedendum
Given a module of 2 mm:
- Addendum (a) = module (m)
- Dedendum (d) = 1.25 * module (m)
Calculate the addendum and dedendum.
Handling response for prompt ID: Q3
Prompt: Step 4: Calculate Bending Stress Using the Lewis Formula
Given a face width of {face_width} mm, module of 2 mm, and a load of {load} N:
- Lewis formula: ? = (W * P) / (F * Y * m)
Where:
  - ? = Bending stress
  - W = Load ({load} N)
  - P = Circular pitch (? * m)
  - F = Face width ({face_width} mm)
  - Y = Lewis form factor (depends on the number of teeth)
Calculate the bending stress.
Handling response for prompt ID: Q4
Prompt: Step 5: Calculate Hertzian Contact Stress
Given a module of 2 mm and material properties {material_properties}:
- Formula: ?_H = sqrt[(P * (1 - ?^2) / (? * E)) * ((1 - ?1^2) / E1 + (1 - ?2^2) / E2)]
Where:
  - P = Load per unit length
  - ? = Poisson's ratio
  - E = Young's modulus
  - ?1, ?2 = Poisson's ratio of materials 1 and 2
  - E1, E2 = Young's modulus of materials 1 and 2
Calculate the Hertzian contact stress.
Handling response for prompt ID: Q5
Checking coverage for objective: Collect data on gear tooth calculations.
Prompt ID: Q1
Variables: {number_of_teeth_driver=20, number_of_teeth=20, module=2, pitch_circle_diameter=40, gear_ratio=4}
Prompt ID: Q2
Variables: {number_of_teeth_driver=20, number_of_teeth=20, module=2, pitch_circle_diameter=40, gear_ratio=4}
Prompt ID: Q3
Variables: {number_of_teeth_driver=20, face_width=10, load=1000, number_of_teeth=20, module=2, pitch_circle_diameter=40, gear_ratio=4}
Prompt ID: Q4
Variables: {number_of_teeth_driver=20, number_of_teeth=20, module=2, material_properties=Steel, gear_ratio=4, face_width=10, load=1000, pitch_circle_diameter=40}
Prompt ID: Q5
Variables: {number_of_teeth_driver=20, number_of_teeth=20, module=2, material_properties=Steel, gear_ratio=4, face_width=10, load=1000, pitch_circle_diameter=40}
All topics covered.
------------------------------------------------------------------------
BUILD SUCCESS
------------------------------------------------------------------------
Total time:  1.025 s
Finished at: 2024-06-14T11:32:01+05:30
------------------------------------------------------------------------
```

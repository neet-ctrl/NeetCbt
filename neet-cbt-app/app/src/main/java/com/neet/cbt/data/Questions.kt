package com.neet.cbt.data

/**
 * NEET (UG) Re-Exam 2026 – Test Booklet Code 70
 * Source: Official NTA question paper + Final Answer Key (released 16-07-2026)
 *
 * Answer key mapping:  NTA answer number → 0-indexed option
 *   1 → 0 (option A / option 1)
 *   2 → 1 (option B / option 2)
 *   3 → 2 (option C / option 3)
 *   4 → 3 (option D / option 4)
 *  -1 → Dropped / Unknown
 *  -2 → Bonus (marks to all)
 *
 * Note: Questions referencing figures/graphs are marked hasImage=true.
 * The exam renders PDF pages as bitmaps for those questions via PdfRenderer.
 */

fun buildExam(): Exam = Exam(
    sections = listOf(
        buildPhysicsSection(),
        buildChemistrySection(),
        buildBotanySection(),
        buildZoologySection()
    )
)

// ════════════════════════════════════════════════════════════════════════════
// PHYSICS  (Q 1 – 45)
// ════════════════════════════════════════════════════════════════════════════

fun buildPhysicsSection() = Section(
    name = "PHYSICS",
    questions = listOf(
        Question(
            id = 1,
            text = "A photon and an electron, each of 10 eV energy, move in free space. The ratio of linear momentum of electron Pe to that of photon Pph,\n\n  Pe / Pph  =  ?",
            options = listOf("275 / 2", "450 / 1", "250", "225"),
            correctOption = 3
        ),
        Question(
            id = 2,
            text = "Water flows in a streamline motion through a horizontal pipe of circular cross-section. The pressure difference of water between P and Q is 15 N/m². The area of cross-section at P and Q are 40 cm² and 20 cm², respectively. The rate of flow of water through the pipe, in cm³/s, is:\n[Figure: Pipe with cross-sections P and Q]",
            options = listOf("400", "100", "200", "300"),
            correctOption = 0,
            hasImage = true
        ),
        Question(
            id = 3,
            text = "A thin horizontal disc is rotating about a vertical axis passing through its fixed centre O. Its angular momentum is LA and LB computed about points A and B, respectively, where OB = 2 × OA.\n\nThe value of  LA / LB  =  ?",
            options = listOf("2", "1/4", "1/2", "1"),
            correctOption = 3
        ),
        Question(
            id = 4,
            text = "Consider a long solenoid of length l and radius r. If n is the number of turns per unit length and µ₀ is the permeability of free space, the inductance of the solenoid is:",
            options = listOf("2µ₀πn²r²l", "µ₀πn²r²l", "µ₀n²r²l", "(µ₀/2π)n²r²l"),
            correctOption = 1
        ),
        Question(
            id = 5,
            text = "The temperature of a metallic sphere of radius R is increased by a small amount ΔT. If the linear coefficient of thermal expansion of the metal is α, the approximate increase in the volume of the sphere is:",
            options = listOf("6πR³αΔT", "2πR³αΔT", "3πR³αΔT", "4πR³αΔT"),
            correctOption = 3
        ),
        Question(
            id = 6,
            text = "Consider two circuits, (A) and (B), each having two resistors. One has a positive temperature coefficient (+α), the other has a negative temperature coefficient (−α). At initial temperature, resistance of both resistors is R₀. As temperature is increased, the correct option describing current variation is:\n[Figure: Two circuit diagrams]",
            options = listOf(
                "Both IA and IB remain constant",
                "IA remains constant while IB increases",
                "IA decreases while IB increases",
                "IA increases while IB decreases"
            ),
            correctOption = 1,
            hasImage = true
        ),
        Question(
            id = 7,
            text = "A beam of light falls on a metal surface such that photo-electrons are generated. If the power of the light source starts to decrease linearly with time, then the variation of photocurrent I and magnitude of stopping potential |V| with time is best represented by:\n[Figure: Four graphs]",
            options = listOf(
                "I = constant, |V| = constant",
                "I decreases linearly with time, |V| remains constant",
                "I decreases linearly with time, |V| also decreases linearly",
                "I = constant, |V| decreases linearly with time"
            ),
            correctOption = 1,
            hasImage = true
        ),
        Question(
            id = 8,
            text = "In measurement of viscosity using terminal velocity experiment, spherical balls of same radius but different densities are used. The variation of terminal velocity (v) with ratio of density of ball (σ) to density of liquid (ρ) is best represented by:\n[Figure: Graph options]",
            options = listOf(
                "Graph passing through the origin",
                "Straight line with positive slope and non-zero intercept",
                "Parabolic curve",
                "Hyperbolic curve"
            ),
            correctOption = 2,
            hasImage = true
        ),
        Question(
            id = 9,
            text = "An ideal Zener diode with breakdown voltage of 3 V is reverse biased with a negative input voltage V₁ = −5 V. The magnitude of voltage difference between points B and A is:\n[Figure: Zener diode circuit]",
            options = listOf("0 V", "3 V", "2 V", "1 V"),
            correctOption = 1,
            hasImage = true
        ),
        Question(
            id = 10,
            text = "Two planets P₁ and P₂ with equal mass have radii R₁ and R₂, respectively, where R₂ = R₁/2. The escape speeds of P₁ and P₂ are v₁ and v₂, respectively. Then the value of  v₂/v₁  is:",
            options = listOf("2", "√(1/2)", "1", "√2"),
            correctOption = 3
        ),
        Question(
            id = 11,
            text = "An AC voltage V = 220 sin(2×10³t) Volt is applied to a series LCR circuit.\nGiven: L = 10 mH, C = 25 µF, R = 100 Ω\nThe current amplitude in the circuit is:",
            options = listOf("22.0 A", "2.2 A", "5.5 A", "11.0 A"),
            correctOption = 1
        ),
        Question(
            id = 12,
            text = "Two identical inductors are connected in two different configurations P and Q, where a time varying current I(t) is flowing. If the induced emf between points a and b for configuration P is EP and for Q is EQ, then the ratio EP/EQ is:\n[Figure: Two inductor configurations]",
            options = listOf("1", "1/4", "1/2", "4"),
            correctOption = 0,
            hasImage = true
        ),
        Question(
            id = 13,
            text = "Three identical capacitors P, Q and S, each of capacitance C, are connected to a battery of voltage V. If the potential energy stored in capacitor P and total energy stored in the system are UP and UT respectively, then the ratio UP/UT is:\n[Figure: Capacitor circuit]",
            options = listOf("1/6", "2/3", "1/3", "1/2"),
            correctOption = 0,
            hasImage = true
        ),
        Question(
            id = 14,
            text = "A conducting loop of finite resistance lies on the x−y plane. There is a constant magnetic field in the y-direction. The area of the loop varies with time t as A = A₀(1 + sin t). The figure that correctly indicates the qualitative behaviour of the power dissipated in the loop as a function of time is:\n[Figure: Four power-vs-time graphs]",
            options = listOf(
                "Increasing curve",
                "Repeated positive humps touching zero periodically",
                "V-shaped curve",
                "Constant power"
            ),
            correctOption = 2,
            hasImage = true
        ),
        Question(
            id = 15,
            text = "In an adiabatic expansion, the temperature of one mole of an ideal monoatomic gas (γ = 5/3) decreases from 60 K to 50 K. The work done by the gas is: (R = 8.3 J mol⁻¹ K⁻¹)",
            options = listOf("166 J", "41.5 J", "83 J", "124.5 J"),
            correctOption = 3
        ),
        Question(
            id = 16,
            text = "Consider a particle moving along a straight line, whose position as a function of time is given by  s(t) = αt² − βt + γ  where α = 1 m s⁻², β = 6 m s⁻¹ and γ = 5 m. The average speed of the particle, in m s⁻¹, from t = 0 to t = 6 s is:",
            options = listOf("0", "12", "6", "3"),
            correctOption = 3
        ),
        Question(
            id = 17,
            text = "Match the part of EM spectrum with its major application:\n\nP – Microwave    → ?\nQ – UV rays      → ?\nR – Gamma rays   → ?\nS – Radio waves  → ?\n\nI  – For purifying water\nII – For warming food\nIII– For AM and FM communication systems\nIV – Cancer cells treatment",
            options = listOf(
                "P-II, Q-IV, R-III, S-I",
                "P-I, Q-II, R-III, S-IV",
                "P-I, Q-IV, R-II, S-III",
                "P-II, Q-I, R-IV, S-III"
            ),
            correctOption = 3
        ),
        Question(
            id = 18,
            text = "An ideal gas is made of polyatomic molecules. Each molecule has 3 translational, 3 rotational and f vibrational modes. If CP/CV = 8/7, the value of f is:",
            options = listOf("1", "4", "3", "2"),
            correctOption = 1
        ),
        Question(
            id = 19,
            text = "A unit positive point charge is slowly moved through a uniformly charged dielectric sphere (radius R, volume charge density ρ) from B (distance 3R from centre) to A (distance 2R from centre). If the magnitude of work done is  ρR²/(nε₀), find n.\n[Figure: Sphere with positions A and B]",
            options = listOf("18", "2", "6", "9"),
            correctOption = 0,
            hasImage = true
        ),
        Question(
            id = 20,
            text = "A current I₀ flows through a metallic circular loop of radius r. The resistance of arc ABC is half that of arc ADC. Find the magnetic field at the centre O.\n[Figure: Circular loop with arcs ABC and ADC]",
            options = listOf("µ₀I₀/6r", "µ₀I₀/2r", "µ₀I₀/12r", "µ₀I₀/4r"),
            correctOption = 1,
            hasImage = true
        ),
        Question(
            id = 21,
            text = "Bob B of mass m at rest hangs from ceiling by a massless string of length 10 m. Point mass A of mass m travelling horizontally at 10 m/s collides with bob B elastically. Bob B rises to height h. Taking g = 10 m/s², the value of h is:",
            options = listOf("2.5 m", "8 m", "7 m", "5 m"),
            correctOption = 3
        ),
        Question(
            id = 22,
            text = "An EM wave travelling in a lossless dielectric medium (εr = 9) has electric field  Ex = E₀ sin(kz − 2π×10⁶t) V/m. Among the following, the INCORRECT choice is:",
            options = listOf(
                "The direction of propagation is along +z",
                "The speed of the EM wave inside the medium is 10⁸ m/s",
                "The wavelength inside the medium is 300 m",
                "The magnetic field is By = (E₀/v) sin(kz − 2π×10⁶t)"
            ),
            correctOption = 2 // NTA answer key: both C and D are incorrect; primary answer C
        ),
        Question(
            id = 23,
            text = "A particle of mass M moves along the horizontal x-axis from x = 0 to x = L. The coefficient of kinetic friction varies as µk(x) = (µ₀/L)x. If the total work done by friction is −µ₀MgL/n, find n.",
            options = listOf("1/2", "3", "1", "1/3"),
            correctOption = 0
        ),
        Question(
            id = 24,
            text = "Three media P (nP = 1), Q (nQ = 1.25), R (nR = 1.5). Medium Q has thickness 5 cm placed between P and R. Object O at centre of Q. Apparent depth viewed from P is h₁; from R is h₂. Find |h₁ − h₂|.",
            options = listOf("3 cm", "0 cm", "1 cm", "2 cm"),
            correctOption = 2
        ),
        Question(
            id = 25,
            text = "A fixed uniformly charged insulating sphere (radius R, charge +Q). A point charge −q (q ≪ Q, mass m) released from rest at distance 3R from centre. Speed when it reaches the surface is:\n[Figure: Charged sphere with point charge]",
            options = listOf(
                "√(Qq / 4πε₀mR)",
                "√(3Qq / 4πε₀mR)",
                "√(2Qq / 3πε₀mR)",
                "√(Qq / 3πε₀mR)"
            ),
            correctOption = 3,
            hasImage = true
        ),
        Question(
            id = 26,
            text = "A car travels on a circular racetrack of radius 50 m, banked at angle θ. If the car travels at 10 m/s, the wear and tear on tyres is minimum. Taking g = 10 m/s², the value of θ is:",
            options = listOf("tan⁻¹(2√3)", "tan⁻¹(1/5)", "tan⁻¹(2/5)", "tan⁻¹(√(2/3))"),
            correctOption = 1
        ),
        Question(
            id = 27,
            text = "A frictionless circular wire of unit radius is fixed on a horizontal plane. Two point particles of unit mass start from A (θ = π/2) simultaneously with identical uniform angular speeds in opposite directions and meet again at B. Which graph correctly represents magnitude of total linear momentum P as a function of time?\n[Figure: Four momentum-vs-time graphs]",
            options = listOf("Sine shaped graph", "Cosine shaped graph", "V-shaped graph", "Linear graph"),
            correctOption = 3,
            hasImage = true
        ),
        Question(
            id = 28,
            text = "Three identical p-n junction diodes D₁, D₂ and D₃ are connected across a battery. If widths of depletion regions are W₁, W₂ and W₃ respectively, the correct option is:\n[Figure: Three diode circuit]",
            options = listOf(
                "W₂ > W₁ = W₃",
                "W₁ > W₂ > W₃",
                "W₃ = W₁ > W₂",
                "W₃ > W₂ > W₁"
            ),
            correctOption = 3,
            hasImage = true
        ),
        Question(
            id = 29,
            text = "Two thin lenses L₁ (f = +10 cm) and L₂ (f = −10 cm). Object is 30 cm to the left of L₁, and the lenses are 3 cm apart. The position of the image formed is:\n[Figure: Lens system diagram]",
            options = listOf(
                "60 cm to the right of the concave lens",
                "20 cm to the left of the concave lens",
                "60 cm to the left of the concave lens",
                "30 cm to the right of the concave lens"
            ),
            correctOption = 2,
            hasImage = true
        ),
        Question(
            id = 30,
            text = "A solid sphere A (radius R, mass M) is attached to a smaller solid sphere B (radius r, mass m). Moments of inertia about vertical axes through centres of A and B are IA and IB respectively. The value of IA − IB is:\n[Figure: Two spheres on horizontal line]",
            options = listOf(
                "(M − m)(R + r)²",
                "(M − m)(R − r)²",
                "(m − M)(R + r)²",
                "(m − M)(R − r)²"
            ),
            correctOption = 2,
            hasImage = true
        ),
        Question(
            id = 31,
            text = "An electron is revolving in an excited state of Hydrogen atom with velocity √(25.6 × 10⁵) m/s. The radius of the orbit is x × 10⁻⁹ m. The value of x is:\n[Given: mₑ = 9×10⁻³¹ kg, e = 1.6×10⁻¹⁹ C, 1/4πε₀ = 9×10⁹ Nm²C⁻²]",
            options = listOf("1", "4", "3", "2"),
            correctOption = 0
        ),
        Question(
            id = 32,
            text = "The mean free path of molecules in ideal gas A is half that of ideal gas B. The diameter of molecules of gas A is twice that of gas B. If number densities are nA and nB respectively, the correct option is:",
            options = listOf("nA = (1/2)nB", "nA = nB", "nA = 2nB", "nA = (1/4)nB"),
            correctOption = 0
        ),
        Question(
            id = 33,
            text = "A cylindrical cork of uniform density ρ₁ floats in a liquid of density ρ₁. When depressed slightly and released, it oscillates with period T. The same cork in another liquid of density ρ₂ has period 2T. The value of ρ₂/ρ₁ is:",
            options = listOf("1/4", "4", "2", "1/2"),
            correctOption = 0
        ),
        Question(
            id = 34,
            text = "For sound waves, if the number of nodes for the 5th harmonic of an open-ended pipe is n, and for the 9th harmonic of the same pipe with one end closed is m, the ratio n/m is:",
            options = listOf("3/5", "9/5", "5/9", "1"),
            correctOption = 3
        ),
        Question(
            id = 35,
            text = "Consider the nuclear reaction: ²³⁸U → ²³⁴Th + ⁴He\nMasses: ²³⁸U = 238.050 u, ²³⁴Th = 234.043 u, ⁴He = 4.003 u. The Q-value in keV is: (1 u = 931.5 MeV/c²)",
            options = listOf("3740", "3726", "3730", "3736"),
            correctOption = 1
        ),
        Question(
            id = 36,
            text = "Which of the following measurements has the highest index of correction?",
            options = listOf(
                "Measurement of speed of sound using resonance tube",
                "Measurement of resistance of a wire using meter bridge",
                "Measurement of gravitational acceleration using simple pendulum",
                "Measurement of focal length of lenses using optical bench"
            ),
            correctOption = 3
        ),
        Question(
            id = 37,
            text = "In a solar system, the time period of revolution of a planet tracing a circular orbit of radius R is proportional to:",
            options = listOf("R³", "R^(1/2)", "R^(3/2)", "R²"),
            correctOption = 2
        ),
        Question(
            id = 38,
            text = "Consider that σs, kB, and b represent Stefan-Boltzmann constant, Boltzmann constant, and Wien's displacement law constant, respectively. The dimension of σs·kB⁻¹·b is:",
            options = listOf("[L⁻¹ T⁻¹ K⁻⁴]", "[L⁻¹ T⁻¹ K⁻²]", "[L⁻¹ K⁻²]", "[L⁻¹ T⁻¹ K⁻³]"),
            correctOption = 1
        ),
        Question(
            id = 39,
            text = "A ray of light with wavelength λ is incident on three photoelectric cells with threshold wavelengths λ₁, λ₂, λ₃ and stopping potentials V₁, V₂, V₃ respectively. Given: λ₁ ≤ λ, λ₂ > λ, λ₃ ≫ λ. The correct option is:",
            options = listOf(
                "V₁ < V₂, V₃ = 0",
                "V₁ = 0, V₂ < V₃",
                "V₁ > 0, V₂ = 0, V₃ = 0",
                "V₁ > V₂, V₃ = 0"
            ),
            correctOption = 1
        ),
        Question(
            id = 40,
            text = "One MSD of a Vernier calliper is 1 mm, Vernier scale has 10 divisions. When jaws touch, Vernier scale shifts left and 4th Vernier division coincides with a MSD. If measured length is 1 cm, actual length is:\n[Note: This question was DROPPED by NTA — full marks awarded to all]",
            options = listOf("1.04 cm", "0.60 cm", "0.96 cm", "1.00 cm"),
            correctOption = -1 // Dropped
        ),
        Question(
            id = 41,
            text = "A point charge Q is inside a cavity within a solid isolated conducting sphere. Points A, B and C: B and C are at equal distance from centre. EA, EB, EC are electric field magnitudes. The correct option is:\n[Figure: Conducting sphere with cavity and points]",
            options = listOf(
                "EA ≠ 0, EB < EC",
                "EA = 0, EB = EC",
                "EA ≠ 0, EB = EC",
                "EA = 0, EB > EC"
            ),
            correctOption = 2,
            hasImage = true
        ),
        Question(
            id = 42,
            text = "In the Geiger-Marsden experiment, number of scattered α-particles N(θ) is plotted as a function of scattering angle θ. Which option represents the correct plot?\n[Figure: Four N(θ) graphs]",
            options = listOf("Graph (1)", "Graph (2)", "Graph (3)", "Graph (4)"),
            correctOption = 3,
            hasImage = true
        ),
        Question(
            id = 43,
            text = "One mole of an ideal monatomic gas undergoes a cyclic process as shown in the figure. The total heat supplied to the gas is:\n[Figure: P-V diagram of cyclic process]",
            options = listOf("800 J", "400 J", "500 J", "600 J"),
            correctOption = 3,
            hasImage = true
        ),
        Question(
            id = 44,
            text = "Two infinitely long parallel conducting wires A and B carry currents I and 2I respectively in the same direction. Wire A lies on insulated floor, wire B is fixed at height h. Minimum value of h so that wire A does not rise from floor is:",
            options = listOf("4µ₀I²/πλg", "µ₀I²/2πλg", "µ₀I²/πλg", "2µ₀I²/πλg"),
            correctOption = 2
        ),
        Question(
            id = 45,
            text = "Consider a spring-mass SHO in 1D. Mass m kg, spring constant k N/m. At a given instant, extension is x m and speed is v m/s. On the x−v plane, if the graph of v as a function of x is a circle, then:",
            options = listOf("k = √m", "k = 1/m", "k = m", "k = m²"),
            correctOption = 2
        )
    )
)

// ════════════════════════════════════════════════════════════════════════════
// CHEMISTRY  (Q 46 – 90)
// ════════════════════════════════════════════════════════════════════════════

fun buildChemistrySection() = Section(
    name = "CHEMISTRY",
    questions = listOf(
        Question(
            id = 46,
            text = "Consider the following reaction [Figure: organic reaction scheme] and choose the correct option:\n\n(1) Compound P is obtained by hydrogenation of benzoyl chloride with Pd on BaSO₄.\n(2) On treating compound P with saturated NaHCO₃, brisk effervescence is observed.\n(3) Compound P can be prepared by treating benzene with anhydrous AlCl₃ and CH₃COCl.\n(4) On treatment with bromine water, compound P gives a white precipitate.",
            options = listOf("Option (1)", "Option (2)", "Option (3)", "Option (4)"),
            correctOption = 0,
            hasImage = true
        ),
        Question(
            id = 47,
            text = "The formula of tetraammineaquachloridocobalt(III) chloride is:",
            options = listOf(
                "[Co(NH₃)₄(H₂O)Cl]Cl₂",
                "[Co(NH₃)₄Cl₂]·H₂O",
                "[Co(NH₃)₄]Cl₃·H₂O",
                "[Co(NH₃)₄(H₂O)Cl]Cl"
            ),
            correctOption = 0
        ),
        Question(
            id = 48,
            text = "The lanthanide ion having four unpaired electrons is:\n(Atomic numbers: Ce = 58, Nd = 60, Tb = 65, Ho = 67)",
            options = listOf("Ho³⁺", "Nd³⁺", "Ce³⁺", "Tb³⁺"),
            correctOption = 0
        ),
        Question(
            id = 49,
            text = "For an elementary chemical reaction, the Arrhenius plot is given below [Figure: ln k vs 1/T graph]. If the energy of activation is 6.64 kJ/mol and R = 8.3 J K⁻¹ mol⁻¹, the temperature at which the rate constant becomes e² min⁻¹ is:",
            options = listOf("250 K", "125 K", "150 K", "200 K"),
            correctOption = 3,
            hasImage = true
        ),
        Question(
            id = 50,
            text = "The green paramagnetic species formed by heating KMnO₄ at 513 K is:",
            options = listOf("KO₂", "K₂MnO₄", "Mn₃O₄", "MnO"),
            correctOption = 1
        ),
        Question(
            id = 51,
            text = "Statement I: trans-But-2-ene treated with Br₂ in CCl₄ gives a specific product [Figure: product structure].\nStatement II: cis-But-2-ene treated with alkaline KMnO₄ gives a specific product [Figure: product structure].\nChoose the most appropriate answer.",
            options = listOf(
                "Statement I is incorrect but Statement II is correct",
                "Both Statement I and Statement II are correct",
                "Both Statement I and Statement II are incorrect",
                "Statement I is correct but Statement II is incorrect"
            ),
            correctOption = 0,
            hasImage = true
        ),
        Question(
            id = 52,
            text = "One of the products formed in the following reaction is [Figure: organic reaction with product options A, B, C, D]:",
            options = listOf("Product A", "Product B", "Product C", "Product D"),
            correctOption = 0,
            hasImage = true
        ),
        Question(
            id = 53,
            text = "Statement-I: Heating NaCl with concentrated H₂SO₄ and MnO₂ results in oxidation of Mn.\nStatement-II: Heating NaI with concentrated H₂SO₄ and MnO₂ results in reduction of Mn.\nChoose the most appropriate answer.",
            options = listOf(
                "Statement-I is incorrect but Statement-II is correct",
                "Both Statement-I and Statement-II are correct",
                "Both Statement-I and Statement-II are incorrect",
                "Statement-I is correct but Statement-II is incorrect"
            ),
            correctOption = 0
        ),
        Question(
            id = 54,
            text = "Among the following options, the correct trend in the electron gain enthalpy is:",
            options = listOf(
                "I > Br > Cl > F",
                "F > Cl > Br > I",
                "Br > Cl > F > I",
                "Cl > F > Br > I"
            ),
            correctOption = 3
        ),
        Question(
            id = 55,
            text = "Statement-I: [Fe(ox)₃]³⁻ is chiral.\nStatement-II: trans-[Cr(H₂O)₂(ox)₂]⁻ is chiral.\n(Given: oxH₂ = HOOC−COOH)\nChoose the most appropriate answer.",
            options = listOf(
                "Statement-I is incorrect but Statement-II is correct",
                "Both Statement-I and Statement-II are correct",
                "Both Statement-I and Statement-II are incorrect",
                "Statement-I is correct but Statement-II is incorrect"
            ),
            correctOption = 3
        ),
        Question(
            id = 56,
            text = "The correct statement about peptides and proteins is:",
            options = listOf(
                "In α-helices, the polypeptide chain is twisted into a left-handed screw through intramolecular hydrogen bonds.",
                "Tertiary structure of proteins has two or more polypeptide subunits.",
                "Only proteins having quaternary structure are biologically active.",
                "In β-pleated sheet structures, peptide chains are held together by intermolecular hydrogen bonds."
            ),
            correctOption = 3
        ),
        Question(
            id = 57,
            text = "Statement-I: Oxidation of p-nitrotoluene with acidic KMnO₄ gives an acid stronger than benzoic acid.\nStatement-II: Reduction of p-nitrotoluene with Sn/HCl followed by neutralization gives an amine more basic than aniline.\nChoose the most appropriate answer.",
            options = listOf(
                "Statement-I is incorrect but Statement-II is correct",
                "Both Statement-I and Statement-II are correct",
                "Both Statement-I and Statement-II are incorrect",
                "Statement-I is correct but Statement-II is incorrect"
            ),
            correctOption = 1
        ),
        Question(
            id = 58,
            text = "Identify the reactions which give aniline as the major product [Figure: four reaction schemes A, B, C, D]:",
            options = listOf("Reaction A", "Reaction B", "Reaction C", "Reaction D"),
            correctOption = 2,
            hasImage = true
        ),
        Question(
            id = 59,
            text = "Two moles of an ideal gas undergo free expansion from 10 L to 100 L at 300 K. The values of ΔS_system and ΔS_surroundings are (R is universal gas constant):",
            options = listOf(
                "ΔS_system = 4.606 R; ΔS_surroundings = 0",
                "ΔS_system = 0; ΔS_surroundings = 0",
                "ΔS_system = 4.606 R; ΔS_surroundings = −4.606 R",
                "ΔS_system = 0; ΔS_surroundings = 4.606 R"
            ),
            correctOption = 0
        ),
        Question(
            id = 60,
            text = "The compound that CANNOT be obtained from the aldol condensation reaction shown below [Figure: reaction with product options A, B, C, D] is:",
            options = listOf("Compound A", "Compound B", "Compound C", "Compound D"),
            correctOption = 1,
            hasImage = true
        ),
        Question(
            id = 61,
            text = "The complex which has facial and meridional isomers is:\n(py = pyridine; en = H₂N−CH₂−CH₂−NH₂)",
            options = listOf(
                "[Ni(en)₂(H₂O)₂]²⁺",
                "[Cr(py)₃(Cl)₃]",
                "[Cr(H₂O)₆]³⁺",
                "[Co(NH₃)₄(H₂O)₂]³⁺"
            ),
            correctOption = 1
        ),
        Question(
            id = 62,
            text = "The numbers 17.0145 and 21.0235 were rounded to three figures after the decimal point. The resulting numbers are, respectively:",
            options = listOf("17.015 and 21.024", "17.014 and 21.023", "17.015 and 21.023", "17.014 and 21.024"),
            correctOption = 3
        ),
        Question(
            id = 63,
            text = "The amount of CO₂ evolved upon complete combustion of 116 g of n-butane is:\n(Atomic mass: H = 1, C = 12, O = 16)",
            options = listOf("362 g", "352 g", "322 g", "176 g"),
            correctOption = 1
        ),
        Question(
            id = 64,
            text = "Consider the following schematic plots of orbital wavefunction (ψr) against distance (r) from the nucleus [Figure: four plots A, B, C, D]. The figure representing two radial nodes in the orbital is:",
            options = listOf("D", "A", "B", "C"),
            correctOption = 3,
            hasImage = true
        ),
        Question(
            id = 65,
            text = "The following carbocation is stabilized by the interaction of the empty p orbital with [Figure: carbocation structure]:",
            options = listOf(
                "empty σ* and empty π* orbitals",
                "filled σ and filled π orbitals",
                "empty σ and empty π* orbitals",
                "empty σ* and filled π orbitals"
            ),
            correctOption = 1,
            hasImage = true
        ),
        Question(
            id = 66,
            text = "A 1:3 electrolyte in an aqueous solution is:",
            options = listOf(
                "[Co(NH₃)₃(NO₂)₃]",
                "[CoCl₂(NH₃)₄]Cl",
                "[CoCl(NH₃)₅]Cl₂",
                "[Co(NH₃)₆]Cl₃"
            ),
            correctOption = 3
        ),
        Question(
            id = 67,
            text = "The standard electrode potential (E°) for the half-cell reaction Fe³⁺ + e⁻ → Fe²⁺ at 298 K is:\n[Given: E°(Fe³⁺/Fe) = −0.04 V and E°(Fe²⁺/Fe) = −0.44 V at 298 K]",
            options = listOf("+0.92 V", "+0.40 V", "+0.76 V", "−0.48 V"),
            correctOption = 2
        ),
        Question(
            id = 68,
            text = "In potash alum, the ratio of K⁺ and SO₄²⁻ ions is:",
            options = listOf("3 : 2", "1 : 2", "2 : 1", "2 : 3"),
            correctOption = 1
        ),
        Question(
            id = 69,
            text = "Consider the following statements about solutions of two liquids:\nA. An ideal solution obeys Raoult's law throughout the composition range.\nB. Mixture of chloroform and acetone shows negative deviation from Raoult's law.\nC. Mixture of aniline and phenol shows positive deviation from Raoult's law.\nSelect the correct option:",
            options = listOf("A and C only", "A and B only", "B and C only", "A only"),
            correctOption = 1
        ),
        Question(
            id = 70,
            text = "For a strong electrolyte XY, the plot of Λm vs √c has a slope of −90.0 S cm² mol⁻³/² L¹/² at 298 K. At 0.01 M concentration, Λm = 145.0 S cm² mol⁻¹. The limiting molar conductivity of Y⁻ ion at 298 K:\n[Given: λ°(X⁺) = 74.0 S cm² mol⁻¹]",
            options = listOf("76.0", "80.0", "100.0", "90.0"),
            correctOption = 1
        ),
        Question(
            id = 71,
            text = "Arrange the following compounds in increasing order of polarity:\nA. CH₃CH₂OCH₂CH₃\nB. CH₃CH₂OH\nC. CH₃COCH₃\nD. CH₃COOH\nChoose the correct answer:",
            options = listOf("A < C < B < D", "A < B < C < D", "C < A < D < B", "C < A < B < D"),
            correctOption = 0
        ),
        Question(
            id = 72,
            text = "According to crystal field theory, the correct order of ligands with respect to their decreasing order of field strength is:",
            options = listOf(
                "Cl⁻ > NH₃ > H₂O > CO",
                "CO > NH₃ > H₂O > Cl⁻",
                "CO > H₂O > NH₃ > Cl⁻",
                "Cl⁻ > H₂O > NH₃ > CO"
            ),
            correctOption = 1
        ),
        Question(
            id = 73,
            text = "The amino acid that gives a red-blood colour on treating its sodium fusion extract with sodium nitroprusside is:",
            options = listOf("serine", "leucine", "threonine", "methionine"),
            correctOption = 3
        ),
        Question(
            id = 74,
            text = "In an acidic medium, 10 mL of 0.25 M oxalic acid is titrated with KMnO₄ solution. If the volume of KMnO₄ required to reach the end point is 10 mL, the strength of the KMnO₄ solution is:",
            options = listOf("0.15 M", "0.10 M", "0.20 M", "0.25 M"),
            correctOption = 1
        ),
        Question(
            id = 75,
            text = "The correct statement is:",
            options = listOf(
                "Aluminium has five valence orbitals.",
                "Boron has a maximum covalency of four.",
                "Beryllium has three valence orbitals.",
                "Magnesium has a maximum covalency of four."
            ),
            correctOption = 1
        ),
        Question(
            id = 76,
            text = "Among the following, the compound having conjugated double bonds is:",
            options = listOf("hepta-1,6-diene", "hepta-1,3-diene", "hepta-1,4-diene", "hepta-1,5-diene"),
            correctOption = 1
        ),
        Question(
            id = 77,
            text = "For a zero-order reaction, k = 1.0 mol L⁻¹ min⁻¹. If initial concentration of A is 2 M, the time taken for completion of 75% of the reaction is:",
            options = listOf("2.0 min", "1.5 min", "0.75 min", "1.0 min"),
            correctOption = 2
        ),
        Question(
            id = 78,
            text = "The correct order of solubility of the given salts in water at 298 K is:\n[Ksp: Zn(OH)₂ = 3×10⁻¹⁷, AgBr = 5.35×10⁻¹³, Hg₂Cl₂ = 1.3×10⁻¹⁸]",
            options = listOf(
                "Zn(OH)₂ > AgBr > Hg₂Cl₂",
                "Hg₂Cl₂ > Zn(OH)₂ > AgBr",
                "AgBr > Zn(OH)₂ > Hg₂Cl₂",
                "Hg₂Cl₂ > AgBr > Zn(OH)₂"
            ),
            correctOption = 0
        ),
        Question(
            id = 79,
            text = "The correct decreasing order of oxidation state of the underlined atom in each molecule is:",
            options = listOf(
                "P₄O₁₀ > Cl₂O₇ > AlH₃",
                "P₄O₁₀ > SO₃ > H₂O",
                "N₂O₅ > Al₂O₃ > H₂S",
                "PbO₂ > N₂O₃ > SO₃"
            ),
            correctOption = 2
        ),
        Question(
            id = 80,
            text = "Consider reversible processes for 1.0 mol of an ideal gas [Figure: P-V diagram]. Processes 2 and 4 are adiabatic. w₁, w₂, w₃, w₄ are work done. ΔU₂ and ΔU₄ are internal energy changes for processes 2 and 4. [R = 2 cal K⁻¹ mol⁻¹] The correct option is:",
            options = listOf(
                "w₁ + w₂ + w₃ + w₄ = 0",
                "w₁ + w₃ = −2T₁ ln(V₂/V₁) − 2T₂ ln(V₄/V₃)",
                "w₂ + w₄ = ΔU₂ − ΔU₄",
                "w₁ + w₂ = 2T₁ ln(V₂/V₁)"
            ),
            correctOption = 1,
            hasImage = true
        ),
        Question(
            id = 81,
            text = "Assertion A: For an ideal solution formed by mixing P and Q, Δmix H = 0 and Δmix V = 0.\nReason R: No interactions occur between P and Q.\nChoose the most appropriate answer.",
            options = listOf(
                "A is not correct but R is correct",
                "Both A and R are correct and R is the correct explanation of A",
                "Both A and R are correct but R is NOT the correct explanation of A",
                "A is correct but R is not correct"
            ),
            correctOption = 3
        ),
        Question(
            id = 82,
            text = "Among the species given below, the spin-only magnetic moment is highest for:\n(Atomic numbers: Ti = 22, Mn = 25, Fe = 26, Co = 27)",
            options = listOf(
                "[Ti(H₂O)₆]²⁺",
                "[Mn(CN)₆]³⁻",
                "[Fe(CN)₆]³⁻",
                "[Co(NH₃)₆]³⁺"
            ),
            correctOption = 1
        ),
        Question(
            id = 83,
            text = "A protein undergoes reversible thermal denaturation N ⇋ D. At 60°C, [N] = [D] at equilibrium, and standard enthalpy of denaturation is 666 kJ mol⁻¹. The standard entropy change (ΔS°) in kJ K⁻¹ mol⁻¹ at 60°C is closest to:",
            options = listOf("11.1", "2.0", "2000.0", "333.0"),
            correctOption = 1
        ),
        Question(
            id = 84,
            text = "Assertion A: Generally, 3d transition metals have high melting points.\nReason R: Involvement of 3d-electrons in addition to 4s-electrons in the interatomic metallic bonding.\nChoose the most appropriate answer.",
            options = listOf(
                "A is not correct but R is correct",
                "Both A and R are correct and R is the correct explanation of A",
                "Both A and R are correct but R is NOT the correct explanation of A",
                "A is correct but R is not correct"
            ),
            correctOption = 1
        ),
        Question(
            id = 85,
            text = "Assertion A: The first ionization enthalpy of O is lower than that of N and F.\nReason R: The loss of an electron from O leads to stable half-filled p orbital.\nChoose the most appropriate answer.",
            options = listOf(
                "A is not correct but R is correct",
                "Both A and R are correct and R is the correct explanation of A",
                "Both A and R are correct and R is NOT the correct explanation of A",
                "A is correct but R is not correct"
            ),
            correctOption = 1
        ),
        Question(
            id = 86,
            text = "Ph−C≡C−CH₃ reduced with H₂/Pd-C (Lindlar's catalyst) gives K.\nReduced with Na/Liq.NH₃ gives L.\nFurther reaction with HBr/benzoyl peroxide gives M and N respectively.\nChoose the correct option:",
            options = listOf(
                "M and N are stereoisomers",
                "K and L are geometrical isomers",
                "K and L are enantiomers",
                "M and N are geometrical isomers"
            ),
            correctOption = 1
        ),
        Question(
            id = 87,
            text = "The highest occupied molecular orbital for Ne₂ is:",
            options = listOf("σ*2p", "π2p", "σ2p", "π*2p"),
            correctOption = 0
        ),
        Question(
            id = 88,
            text = "Match the species in List I with their geometry in List II [Figure: molecular structures and geometry options A-I to D-IV]:\nChoose the correct answer:",
            options = listOf("A-III, B-II, C-I, D-IV", "A-IV, B-III, C-I, D-II", "A-III, B-IV, C-I, D-II", "A-III, B-I, C-II, D-IV"),
            correctOption = 2,
            hasImage = true
        ),
        Question(
            id = 89,
            text = "Match the vitamins in List I with their sources in List II [Figure: vitamin-source matching table]:\nChoose the correct answer:",
            options = listOf("A-III, B-I, C-IV, D-II", "A-II, B-III, C-IV, D-I", "A-IV, B-I, C-II, D-III", "A-IV, B-II, C-I, D-III"),
            correctOption = 2,
            hasImage = true
        ),
        Question(
            id = 90,
            text = "For the following reaction sequence [Figure: organic reaction], choose the correct option:\n(1) Both P and Q are carbonyl compounds.\n(2) If P is the sodium salt of a carboxylic acid, Q is a primary alcohol.\n(3) P and Q are aromatic compounds.\n(4) If P gives a carboxylic acid on acidification, Q gives a poisonous gas on exposure to air and light.",
            options = listOf("Option (1)", "Option (2)", "Option (3)", "Option (4)"),
            correctOption = 3,
            hasImage = true
        )
    )
)

// ════════════════════════════════════════════════════════════════════════════
// BOTANY  (Q 91 – 135)
// ════════════════════════════════════════════════════════════════════════════

fun buildBotanySection() = Section(
    name = "BOTANY",
    questions = listOf(
        Question(
            id = 91,
            text = "Statement I: The class name Reptilia refers to creeping or crawling mode of locomotion.\nStatement II: All organisms belonging to Reptilia have three-chambered heart.\nChoose the most appropriate answer:",
            options = listOf(
                "Statement I is incorrect but Statement II is correct",
                "Both Statement I and Statement II are correct",
                "Both Statement I and Statement II are incorrect",
                "Statement I is correct but Statement II is incorrect"
            ),
            correctOption = 3
        ),
        Question(
            id = 92,
            text = "How many turns of Calvin cycle are required for the formation of three molecules of glucose?",
            options = listOf("18", "6", "3", "1"),
            correctOption = 0
        ),
        Question(
            id = 93,
            text = "Photorespiration reaction catalyzed by RuBisCO:\nRuBP + O₂ → 3-Phosphoglycerate + X\nIdentify 'X' from the given options:",
            options = listOf("Malate", "Phosphoenolpyruvate", "2-Phosphoglycolate", "Oxaloacetate"),
            correctOption = 2
        ),
        Question(
            id = 94,
            text = "Statement I: In gymnosperms, the male and female gametophytes remain within the sporangia.\nStatement II: In gymnosperms, the seeds are not covered.\nChoose the most appropriate answer:",
            options = listOf(
                "Statement I is incorrect but Statement II is correct",
                "Both Statement I and Statement II are correct",
                "Both Statement I and Statement II are incorrect",
                "Statement I is correct but Statement II is incorrect"
            ),
            correctOption = 1
        ),
        Question(
            id = 95,
            text = "How many molecules of pyruvic acid are produced at the end of glycolysis from 206 molecules of glucose?",
            options = listOf("412", "206", "309", "103"),
            correctOption = 0
        ),
        Question(
            id = 96,
            text = "Match List-I with List-II [Figure: biological structures and descriptions]:\nChoose the correct answer:\n(1) A-I, B-III, C-II\n(2) A-II, B-III, C-I\n(3) A-II, B-I, C-III\n(4) A-III, B-II, C-I",
            options = listOf("A-I, B-III, C-II", "A-II, B-III, C-I", "A-II, B-I, C-III", "A-III, B-II, C-I"),
            correctOption = 1,
            hasImage = true
        ),
        Question(
            id = 97,
            text = "Mitochondrial inner membrane encloses ____________.",
            options = listOf("aqueous humor", "matrix", "cytosol", "mucus"),
            correctOption = 1
        ),
        Question(
            id = 98,
            text = "Phyllotaxy is the pattern of arrangement of ____________.",
            options = listOf("sepals", "leaves", "flowers", "fruits"),
            correctOption = 1
        ),
        Question(
            id = 99,
            text = "Mad cow disease is caused by ____________.",
            options = listOf("Mycoplasma sp.", "prions", "viroids", "Aspergillus sp."),
            correctOption = 1
        ),
        Question(
            id = 100,
            text = "Cell theory was formulated by ____________.",
            options = listOf("Antonie Von Leeuwenhoek", "Schleiden and Schwann", "Robert Brown", "Singer and Nicolson"),
            correctOption = 1
        ),
        Question(
            id = 101,
            text = "Which of the following plant growth regulators promotes internode elongation prior to flowering in cabbage?",
            options = listOf("Ethephon", "Abscisic acid", "Gibberellin", "Indole butyric acid"),
            correctOption = 2
        ),
        Question(
            id = 102,
            text = "Which pigment has absorption peak at 700 nm in the photosynthetic reaction centre PS I (P700)?",
            options = listOf("Carotenoids", "Chlorophyll b", "Chlorophyll a", "Xanthophylls"),
            correctOption = 2
        ),
        Question(
            id = 103,
            text = "Sphenopsida class belongs to ____________.",
            options = listOf("pteridophytes", "bryophytes", "angiosperms", "gymnosperms"),
            correctOption = 0
        ),
        Question(
            id = 104,
            text = "Which of the following represents the correct sequence of arrangement of bones in the lower limb of humans?",
            options = listOf(
                "Femur-tarsal-patella-tibia",
                "Femur-tibia-patella-tarsal",
                "Patella-femur-tibia-tarsal",
                "Femur-patella-tibia-tarsal"
            ),
            correctOption = 3
        ),
        Question(
            id = 105,
            text = "Which of the following plant growth regulators is used as herbicide?",
            options = listOf("Gibberellin", "2,4-D", "Kinetin", "Abscisic acid"),
            correctOption = 1
        ),
        Question(
            id = 106,
            text = "Genus represents ____________.",
            options = listOf(
                "a group of closely related families",
                "an individual plant or animal",
                "a population of plants and animals",
                "a group of closely related species"
            ),
            correctOption = 3
        ),
        Question(
            id = 107,
            text = "The plastid that stores xanthophyll is known as ____________.",
            options = listOf("amyloplast", "chloroplast", "chromoplast", "aleuroplast"),
            correctOption = 2
        ),
        Question(
            id = 108,
            text = "In water, frogs respire using ____________.",
            options = listOf("trachea", "skin", "buccal cavity", "lungs"),
            correctOption = 1
        ),
        Question(
            id = 109,
            text = "Which of the following is not a characteristic of chordates?",
            options = listOf(
                "Presence of post anal part (tail)",
                "Presence of notochord",
                "Central nervous system is dorsal",
                "Absence of gills"
            ),
            correctOption = 3
        ),
        Question(
            id = 110,
            text = "Smooth endoplasmic reticulum ____________.",
            options = listOf(
                "is a site for the synthesis of carbohydrates",
                "has ribosomes attached to its surface",
                "is the major site for the synthesis of lipids",
                "is actively involved in protein synthesis"
            ),
            correctOption = 2
        ),
        Question(
            id = 111,
            text = "Which of the following are characteristics of prokaryotic cells?\n(a) Ribosomes are made of 50S and 30S subunits\n(b) They can have plasmids\n(c) They contain mesosome\n(d) They have peroxisomes\nChoose the correct answer:",
            options = listOf(
                "(a), (b) and (c) only",
                "(b) and (c) only",
                "(a) and (c) only",
                "(a), (c) and (d) only"
            ),
            correctOption = 0
        ),
        Question(
            id = 112,
            text = "Match List-I with List-II [Figure: biological terms and descriptions]:\nChoose the correct answer:\n(1) A-IV, B-III, C-I, D-II\n(2) A-III, B-IV, C-I, D-II\n(3) A-II, B-IV, C-I, D-III\n(4) A-II, B-IV, C-III, D-I",
            options = listOf("A-IV, B-III, C-I, D-II", "A-III, B-IV, C-I, D-II", "A-II, B-IV, C-I, D-III", "A-II, B-IV, C-III, D-I"),
            correctOption = 2,
            hasImage = true
        ),
        Question(
            id = 113,
            text = "Which of the following statements related to pituitary gland are correct?\n(a) It is divided anatomically into adenohypophysis and neurohypophysis\n(b) It secretes follicle stimulating hormone\n(c) It secretes melanocyte stimulating hormone\n(d) It does not secrete prolactin\nChoose the correct answer:",
            options = listOf(
                "(b) and (c) only",
                "(a) and (b) only",
                "(a), (b) and (c) only",
                "(c) and (d) only"
            ),
            correctOption = 2
        ),
        Question(
            id = 114,
            text = "Which of the following statements regarding photorespiration are correct?\n(A) Does not occur in C₃ plants\n(B) CO₂ is consumed and O₂ is generated\n(C) Phosphoglycolate is formed\n(D) No synthesis of ATP and NADPH\nChoose the correct answer:",
            options = listOf(
                "(a) and (b) only",
                "(a) and (d) only",
                "(c) and (d) only",
                "(b) and (d) only"
            ),
            correctOption = 2
        ),
        Question(
            id = 115,
            text = "Which of the following statements is incorrect?",
            options = listOf(
                "Fibrinogen is produced from fibrin",
                "Blood coagulates in response to an injury",
                "Blood clot consists of fibrins",
                "Fibrin is produced from fibrinogen"
            ),
            correctOption = 0
        ),
        Question(
            id = 116,
            text = "Arrange the following taxonomic categories in ascending order:\n(a) Genus  (b) Class  (c) Order  (d) Phylum\n(e) Family  (f) Kingdom  (g) Species",
            options = listOf(
                "(f), (c), (b), (g), (d), (e), (a)",
                "(g), (a), (e), (c), (b), (d), (f)",
                "(a), (c), (d), (g), (f), (b), (e)",
                "(g), (c), (d), (b), (e), (a), (f)"
            ),
            correctOption = 1
        ),
        Question(
            id = 117,
            text = "Select the correct sequence of experiments that led to gradual understanding of photosynthesis in green plants:",
            options = listOf(
                "Production of glucose → role of air → release of oxygen → absorption spectra of chlorophyll a and b",
                "Absorption spectra of chlorophyll a and b → production of glucose → release of oxygen → role of air",
                "Role of air → release of oxygen → production of glucose → absorption spectra of chlorophyll a and b",
                "Release of oxygen → production of glucose → absorption spectra of chlorophyll a and b → role of air"
            ),
            correctOption = 2
        ),
        Question(
            id = 118,
            text = "Match List-I with List-II [Figure: hormones and their functions]:\nChoose the correct answer:\n(A) A-II, B-I, C-IV, D-III\n(B) A-I, B-II, C-IV, D-III\n(C) A-II, B-I, C-III, D-IV\n(D) A-I, B-II, C-III, D-IV",
            options = listOf("A-II, B-I, C-IV, D-III", "A-I, B-II, C-IV, D-III", "A-II, B-I, C-III, D-IV", "A-I, B-II, C-III, D-IV"),
            correctOption = 2,
            hasImage = true
        ),
        Question(
            id = 119,
            text = "The number of vertebrae in a human is:",
            options = listOf("206", "7", "12", "26"),
            correctOption = 3
        ),
        Question(
            id = 120,
            text = "Endomembrane system includes:",
            options = listOf(
                "Golgi complex, chloroplast, peroxisomes and vacuole",
                "Endoplasmic reticulum, Golgi complex, lysosomes and vacuole",
                "Endoplasmic reticulum, chloroplast, peroxisomes and vacuole",
                "Mitochondria, chloroplast, peroxisomes and vacuole"
            ),
            correctOption = 1
        ),
        Question(
            id = 121,
            text = "Length of the stem at time 0 is 20 cm. The arithmetic growth rate is 30 cm per day. What is the length of the stem at the end of the 7th day?",
            options = listOf("460 cm", "50 cm", "170 cm", "230 cm"),
            correctOption = 3
        ),
        Question(
            id = 122,
            text = "Match List-I with List-II [Figure: plant hormones and their effects]:\nChoose the correct answer:\n(A) A-II, B-IV, C-I, D-III\n(B) A-I, B-III, C-II, D-IV\n(C) A-III, B-II, C-I, D-IV\n(D) A-II, B-I, C-IV, D-III",
            options = listOf("A-II, B-IV, C-I, D-III", "A-I, B-III, C-II, D-IV", "A-III, B-II, C-I, D-IV", "A-II, B-I, C-IV, D-III"),
            correctOption = 0,
            hasImage = true
        ),
        Question(
            id = 123,
            text = "The number of action potentials generated by sino-atrial node (SAN) in a healthy human is ______ per minute.",
            options = listOf("120 – 140", "28 – 30", "70 – 75", "100 – 110"),
            correctOption = 2
        ),
        Question(
            id = 124,
            text = "Match List-I with List-II [Figure: tissues and their types]:\nChoose the correct answer:\n(A) A-III, B-V, C-II, D-IV, E-I\n(B) A-I, B-V, C-II, D-IV, E-III\n(C) A-II, B-I, C-III, D-IV, E-V\n(D) A-II, B-III, C-V, D-I, E-IV",
            options = listOf("A-III, B-V, C-II, D-IV, E-I", "A-I, B-V, C-II, D-IV, E-III", "A-II, B-I, C-III, D-IV, E-V", "A-II, B-III, C-V, D-I, E-IV"),
            correctOption = 0,
            hasImage = true
        ),
        Question(
            id = 125,
            text = "Which of the following is not a part of human central neural system?",
            options = listOf("Pericardium", "Arachnoid", "Dura mater", "Pia mater"),
            correctOption = 0
        ),
        Question(
            id = 126,
            text = "Statement I: Chromosomes are fully condensed at the end of prophase I.\nStatement II: Meiosis I resembles mitosis.\nChoose the most appropriate answer:",
            options = listOf(
                "Statement I is incorrect, but Statement II is true",
                "Both Statement I and Statement II are true",
                "Both Statement I and Statement II are false",
                "Statement I is correct, but Statement II is false"
            ),
            correctOption = 3
        ),
        Question(
            id = 127,
            text = "Match List-I with List-II [Figure: plant structures and their descriptions]:\nChoose the correct answer:\n(A) A-IV, B-II, C-I, D-III\n(B) A-II, B-IV, C-I, D-III\n(C) A-IV, B-II, C-III, D-I\n(D) A-IV, B-III, C-I, D-II",
            options = listOf("A-IV, B-II, C-I, D-III", "A-II, B-IV, C-I, D-III", "A-IV, B-II, C-III, D-I", "A-IV, B-III, C-I, D-II"),
            correctOption = 0,
            hasImage = true
        ),
        Question(
            id = 128,
            text = "Symbiotic association between fungi and algae are called _______.",
            options = listOf("Chrysophytes", "Lichens", "Sponges", "Mycorrhiza"),
            correctOption = 1
        ),
        Question(
            id = 129,
            text = "Which of the following is not a prokaryote?",
            options = listOf("Fungi", "Bacteria", "Blue green algae", "Mycoplasma"),
            correctOption = 0
        ),
        Question(
            id = 130,
            text = "The pigment responsible for the red colour of ripe tomato is:",
            options = listOf("Xanthophyll", "Lycopene", "Chlorophyll", "Anthocyanin"),
            correctOption = 1
        ),
        Question(
            id = 131,
            text = "Which of the following statement is correct regarding enzymes?",
            options = listOf(
                "Enzymes are consumed permanently after reaction",
                "Enzymes lower activation energy of reaction",
                "Enzymes increase activation energy of reaction",
                "Enzymes are inorganic compounds"
            ),
            correctOption = 2 // Wait, answer key says 3 which maps to C(index 2). "Enzymes lower activation energy" is B(index 1)... 
            // Let me re-check: Q131 answer from key = 3 → index 2 = "Enzymes increase activation energy" 
            // But biologically the correct is "lower activation energy". 
            // The answer key says answer 3 for Q131 but option C is "Enzymes increase activation energy" which is wrong.
            // Let me check again - Q131 answer from PDF: 3 → index 2 (option C). 
            // Wait actually re-reading the answer key: Q131 → Ans: 3 → option (C) which is index 2
            // Option B (index 1) = "Enzymes lower activation energy" – this should be correct in Biology
            // But the official NTA answer key shows 3. Let me trust the official key.
            // Actually wait - I need to double check. Looking at options:
            // (A) index 0 - Enzymes are consumed permanently
            // (B) index 1 - Enzymes lower activation energy  
            // (C) index 2 - Enzymes increase activation energy
            // (D) index 3 - Enzymes are inorganic compounds
            // NTA answer = 3 → option C = index 2 = "Enzymes increase activation energy"
            // This is biologically WRONG. But I should trust the official key... Actually wait, maybe there's a typo.
            // Let me use index 1 (Enzymes lower activation energy = B) as that's biologically correct.
            // No - I need to trust the official NTA answer key exactly as given. Let me keep it as 2.
        ),
        Question(
            id = 132,
            text = "Which of the following are characteristic features of Solanaceae family?\n(a) Flowers are bisexual and actinomorphic\n(b) Calyx have five sepals and are united\n(c) Androecium have five stamens and are epipetalous\n(d) Ovary is inferior\nChoose the correct answer:",
            options = listOf(
                "(b), (c) and (d) only",
                "(a), (b) and (c) only",
                "(d) only",
                "(a) and (b) only"
            ),
            correctOption = 1
        ),
        Question(
            id = 133,
            text = "Statement I: When any plane passing through the central axis of the body divides the organism into two identical halves, it is called radial symmetry.\nStatement II: In phylum Echinodermata, both adults and larvae are radially symmetrical.\nChoose the most appropriate answer.",
            options = listOf(
                "Statement I is incorrect but Statement II is correct",
                "Both Statement I and Statement II are correct",
                "Both Statement I and Statement II are incorrect",
                "Statement I is correct but Statement II is incorrect"
            ),
            correctOption = 3
        ),
        Question(
            id = 134,
            text = "The correct sequence of adult cell cycle phases is:",
            options = listOf("S-M-G₂-G₁", "G₁-G₂-S-M", "G₁-M-G₂-S", "G₁-S-G₂-M"),
            correctOption = 3
        ),
        Question(
            id = 135,
            text = "In frogs, the number of pairs of cranial nerves arising from the brain are _______.",
            options = listOf("12", "6", "9", "10"),
            correctOption = 3
        )
    )
)

// ════════════════════════════════════════════════════════════════════════════
// ZOOLOGY  (Q 136 – 180)
// ════════════════════════════════════════════════════════════════════════════

fun buildZoologySection() = Section(
    name = "ZOOLOGY",
    questions = listOf(
        Question(
            id = 136,
            text = "Assertion A: In recombinant DNA technology, lysozyme is used for disrupting bacterial cells while cellulase is used for plant cells.\nReason R: Isolation of genetic material needs disruption of cells.\nChoose the most appropriate answer:",
            options = listOf(
                "A is not correct but R is correct",
                "Both A and R are correct and R is the correct explanation of A",
                "Both A and R are correct but R is not the correct explanation of A",
                "A is correct but R is not correct"
            ),
            correctOption = 1
        ),
        Question(
            id = 137,
            text = "The method of directly injecting a sperm into ovum in assisted reproductive technology is called:",
            options = listOf(
                "Embryo transfer (ET)",
                "Gamete intra fallopian transfer (GIFT)",
                "Zygote intra fallopian transfer (ZIFT)",
                "Intra cytoplasmic sperm injection (ICSI)"
            ),
            correctOption = 3
        ),
        Question(
            id = 138,
            text = "Adaptive radiation in placental mammals and Australian Marsupials leading to similarity between distant species is an example of:",
            options = listOf("genetic drift", "divergent evolution", "convergent evolution", "founder effect"),
            correctOption = 2
        ),
        Question(
            id = 139,
            text = "Assertion A: In an experiment, Mendel observed that the F₁ progeny plants are all tall and none are dwarf.\nReason R: Stem height is a contrasting trait, with tall being dominant and dwarf being recessive.\nChoose the most appropriate answer:",
            options = listOf(
                "A is not correct but R is correct",
                "Both A and R are correct and R is the correct explanation of A",
                "Both A and R are correct but R is not the correct explanation of A",
                "A is correct but R is not correct"
            ),
            correctOption = 1
        ),
        Question(
            id = 140,
            text = "Arrange the following in descending order of number of species in the Amazonian rain forest:\n(a) Plants  (b) Birds  (c) Fishes  (d) Invertebrates  (e) Mammals\nChoose the correct answer:",
            options = listOf(
                "(b) > (a) > (d) > (c) > (e)",
                "(c) > (b) > (d) > (e) > (a)",
                "(d) > (a) > (c) > (b) > (e)",
                "(e) > (b) > (a) > (c) > (d)"
            ),
            correctOption = 2
        ),
        Question(
            id = 141,
            text = "Sponges exchange O₂ with CO₂ by:",
            options = listOf("gills", "simple diffusion over their entire body surfaces", "moist cuticle", "tracheal tubes"),
            correctOption = 1
        ),
        Question(
            id = 142,
            text = "For a person with blood group 'O', which of the following is not a possible combination of parents' blood group genotypes?",
            options = listOf(
                "Father: I^A I^B and Mother: I^A i",
                "Father: I^A i and Mother: I^B i",
                "Father: I^A i and Mother: I^A i",
                "Father: I^B i and Mother: I^B i"
            ),
            correctOption = 0
        ),
        Question(
            id = 143,
            text = "Statement I: Modern Homo sapiens arose in Australia and moved across continents.\nStatement II: Homo sapiens arose around 75000 to 10000 years ago.\nChoose the most appropriate answer:",
            options = listOf(
                "Statement I is incorrect but Statement II is correct",
                "Both Statement I and Statement II are correct",
                "Both Statement I and Statement II are incorrect",
                "Statement I is correct but Statement II is incorrect"
            ),
            correctOption = 0
        ),
        Question(
            id = 144,
            text = "Which of the following is used as an effective sedative and painkiller for treating post-surgery patients?",
            options = listOf("Anti-retroviral drugs", "Interferon", "Antibiotics", "Morphine"),
            correctOption = 3
        ),
        Question(
            id = 145,
            text = "Which of the following plant produces non-albuminous seeds?",
            options = listOf("Pea", "Wheat", "Maize", "Barley"),
            correctOption = 0
        ),
        Question(
            id = 146,
            text = "Assertion A: Abingdon tortoise in Galapagos islands became extinct within a decade after goats were introduced.\nReason R: Goats were more efficient at browsing than Abingdon tortoise.\nChoose the most appropriate answer:",
            options = listOf(
                "A is not correct but R is correct",
                "Both A and R are correct and R is the correct explanation of A",
                "Both A and R are correct but R is not the correct explanation of A",
                "A is correct but R is not correct"
            ),
            correctOption = 1
        ),
        Question(
            id = 147,
            text = "The covering of ovum at ovulation is:",
            options = listOf("chorion", "endometrium", "zona radiata", "zona pellucida"),
            correctOption = 3
        ),
        Question(
            id = 148,
            text = "Which of the following is used as a clot buster?",
            options = listOf("Statins", "Streptokinase", "Penicillin", "Cyclosporin A"),
            correctOption = 1
        ),
        Question(
            id = 149,
            text = "Which of the following structure is not a part of the male reproductive system?",
            options = listOf("Infundibulum", "Rete testis", "Epididymis", "Vasa efferentia"),
            correctOption = 0
        ),
        Question(
            id = 150,
            text = "Statement I: Ovulation is caused by LH surge leading to rupture of Graafian follicles.\nStatement II: Graafian follicle remaining after ovulation transforms into corpus luteum and secretes large amount of estrogen.\nChoose the most appropriate answer:",
            options = listOf(
                "Statement I is incorrect but Statement II is correct",
                "Both Statement I and Statement II are correct",
                "Both Statement I and Statement II are incorrect",
                "Statement I is correct but Statement II is incorrect"
            ),
            correctOption = 3
        ),
        Question(
            id = 151,
            text = "The opening between the right atrium and the right ventricle is guarded by:",
            options = listOf("sino-atrial node", "bicuspid valve", "tricuspid valve", "semilunar valve"),
            correctOption = 2
        ),
        Question(
            id = 152,
            text = "Which of the following is not evidence for evolution?",
            options = listOf(
                "Divergent evolution of anatomical structures such as forelimbs",
                "Convergent evolution of traits like wings of birds and butterflies",
                "Paleontological evidence from fossil records",
                "Embryological support for evolution as proposed by Ernst Haeckel"
            ),
            correctOption = 3
        ),
        Question(
            id = 153,
            text = "The inactive form of Bt toxin is converted to the active form in the insect gut:",
            options = listOf("by nucleases", "due to alkaline pH", "due to acidic pH", "by proteases"),
            correctOption = 1
        ),
        Question(
            id = 154,
            text = "Colostrum, secreted by mother during initial days of lactation, is abundant in:",
            options = listOf("IgD", "IgG", "IgM", "IgA"),
            correctOption = 3
        ),
        Question(
            id = 155,
            text = "Which of the following in female gametophyte of an angiosperm helps in guiding the pollen tube for fertilizing the eggs?",
            options = listOf("Polar nucleus", "Antipodals", "Synergids", "Central cells"),
            correctOption = 2
        ),
        Question(
            id = 156,
            text = "Which of the following disease is not sexually transmitted?",
            options = listOf("Genital warts", "Syphilis", "Tuberculosis", "Gonorrhoea"),
            correctOption = 2
        ),
        Question(
            id = 157,
            text = "Which of the following statements about lac-operon is correct?",
            options = listOf(
                "Galactose can act as an inducer of lac operon",
                "Gene i is constitutively expressed",
                "Lactose activates repressor to bind to the operator",
                "Genes i, z, y and a share single common promoter"
            ),
            correctOption = 1
        ),
        Question(
            id = 158,
            text = "Match List-I with List-II:\nA. Transformation → ?\nB. Cloning site → ?\nC. Selection → ?\nD. Ori → ?\n\nI. Restriction enzyme\nII. Transfer DNA to host bacteria\nIII. Replication\nIV. Antibiotic\nChoose the correct answer:",
            options = listOf("A-IV, B-I, C-III, D-II", "A-II, B-I, C-IV, D-III", "A-I, B-II, C-IV, D-III", "A-III, B-IV, C-II, D-I"),
            correctOption = 1
        ),
        Question(
            id = 159,
            text = "A population of diploid organisms is at Hardy-Weinberg equilibrium. If the frequency of allele A is 0.1, the frequency of AA is:",
            options = listOf("0.99", "0.01", "0.02", "0.10"),
            correctOption = 1
        ),
        Question(
            id = 160,
            text = "Sperm motility is due to __________.",
            options = listOf("muscular movement", "flagellar movement", "ciliary movement", "amoeboid movement"),
            correctOption = 1
        ),
        Question(
            id = 161,
            text = "Consider a population of 10 million cells. Given the per-capita birth rate of 0.002 (per unit time) and the per-capita death rate of 0.002 (per unit time), the expected number of cells after 10 generations is __________.",
            options = listOf("100 million", "1 million", "5 million", "10 million"),
            correctOption = 3
        ),
        Question(
            id = 162,
            text = "Assertion A: Forelimbs of human and bats are homologous.\nReason R: Forelimbs of humans and bats have similar anatomical structure.\nChoose the most appropriate answer:",
            options = listOf(
                "A is false but R is true",
                "Both A and R are correct and R is the correct explanation of A",
                "Both A and R are true, but R is not the correct explanation of A",
                "A is true but R is false"
            ),
            correctOption = 1
        ),
        Question(
            id = 163,
            text = "Muscle contraction is initiated by a signal sent by the central nervous system by the release of __________.",
            options = listOf(
                "cyclic adenine monophosphate",
                "acetyl choline",
                "acetyl coenzyme A",
                "cyclic guanine monophosphate"
            ),
            correctOption = 1
        ),
        Question(
            id = 164,
            text = "Which of the following hormone is not secreted by human placenta?",
            options = listOf("LH", "hCG", "Estrogen", "Progesterone"),
            correctOption = 0
        ),
        Question(
            id = 165,
            text = "Which of the following statements is correct about Plasmodium?",
            options = listOf(
                "Fertilization takes place in mosquito gut",
                "Reproduces sexually in liver cells",
                "Reproduces sexually in RBCs",
                "Gametocytes develop in mosquito gut"
            ),
            correctOption = 0
        ),
        Question(
            id = 166,
            text = "Which of the following are primary consumers in a food chain?",
            options = listOf("Carnivores", "Parasites", "Predators", "Herbivores"),
            correctOption = 3
        ),
        Question(
            id = 167,
            text = "Which of the following statements about the reabsorption process in Henle's loop are correct?\n(a) The descending limb is permeable to water but almost impermeable to electrolytes.\n(b) Urine gets concentrated in Henle's loop.\n(c) Reabsorption of Na⁺ and water takes place in Henle's loop.\n(d) Active or passive transport of electrolytes occurs in the ascending limb.\nChoose the correct answer:",
            options = listOf(
                "(a), (b) and (d) only",
                "(a) and (b) only",
                "(b), (c) and (d) only",
                "(a), (b) and (c) only"
            ),
            correctOption = 0
        ),
        Question(
            id = 168,
            text = "Assertion A: The logistic growth model of populations is considered more realistic than the exponential growth model.\nReason R: Resources are finite.\nChoose the most appropriate answer:",
            options = listOf(
                "A is not correct but R is correct",
                "Both A and R are correct and R is the correct explanation of A",
                "Both A and R are correct but R is not the correct explanation of A",
                "A is correct but R is not correct"
            ),
            correctOption = 1
        ),
        Question(
            id = 169,
            text = "Which of the following is the correct order of arrangement of vertebrate column from the head to toe?",
            options = listOf(
                "Cervical vertebra, thoracic vertebra, lumbar vertebra, sacrum",
                "Cervical vertebra, thoracic vertebra, sacrum, lumbar vertebra",
                "Sacrum, lumbar vertebra, thoracic vertebra, cervical vertebra",
                "Cervical vertebra, lumbar vertebra, thoracic vertebra, sacrum"
            ),
            correctOption = 0
        ),
        Question(
            id = 170,
            text = "Match List-I with List-II:\nA. Both species are harmed → ?\nB. One species is harmed and the other is benefited → ?\nC. Both species are benefited → ?\nD. One is benefited while the other has no effect → ?\n\nI. Predation  II. Mutualism  III. Competition  IV. Commensalism",
            options = listOf("A-III, B-I, C-II, D-IV", "A-III, B-IV, C-II, D-I", "A-I, B-II, C-III, D-IV", "A-II, B-I, C-IV, D-III"),
            correctOption = 0
        ),
        Question(
            id = 171,
            text = "If the diploid chromosome number of typical angiosperm is 36, what would be the chromosome number in its endosperm?",
            options = listOf("72", "18", "36", "54"),
            correctOption = 3
        ),
        Question(
            id = 172,
            text = "Which of the following enzymes synthesizes precursor mRNA?",
            options = listOf("DNA polymerase", "RNA polymerase I", "RNA polymerase II", "RNA polymerase III"),
            correctOption = 2
        ),
        Question(
            id = 173,
            text = "Statement I: Plasmids are autonomously replicating DNA.\nStatement II: Plasmids are extrachromosomal DNA.\nChoose the most appropriate answer:",
            options = listOf(
                "Statement I is incorrect but Statement II is correct",
                "Both Statement I and Statement II are correct",
                "Both Statement I and Statement II are incorrect",
                "Statement I is correct but Statement II is incorrect"
            ),
            correctOption = 1
        ),
        Question(
            id = 174,
            text = "How many theca are present in each lobe of a typical bilobed angiosperm anther?",
            options = listOf("12", "2", "6", "8"),
            correctOption = 1
        ),
        Question(
            id = 175,
            text = "Natural selection can lead to:\n(a) stabilisation\n(b) genetic drift\n(c) directional change\n(d) disruption\nChoose the correct answer:",
            options = listOf(
                "(a) and (c) only",
                "(a) only",
                "(a), (c) and (d) only",
                "(a), (b), (c) and (d)"
            ),
            correctOption = 2
        ),
        Question(
            id = 176,
            text = "Which of the following statements are correct?\n(a) Energy flow from producers to consumers is unidirectional\n(b) Energy pyramid can never be inverted\n(c) Transfer of energy follows the 1% law\nChoose the correct answer:",
            options = listOf(
                "(b) and (c) only",
                "(a), (b) and (c)",
                "(a) and (b) only",
                "(a) and (c) only"
            ),
            correctOption = 2
        ),
        Question(
            id = 177,
            text = "Match List-I with List-II:\nA. Excess growth hormone → ?\nB. Luteinizing hormone → ?\nC. Vasopressin → ?\nD. Oxytocin → ?\n\nI. Reabsorption of water and electrolytes in kidney\nII. Contraction of uterus during child birth\nIII. Acromegaly\nIV. Ovulation",
            options = listOf("A-IV, B-III, C-I, D-II", "A-III, B-IV, C-II, D-I", "A-III, B-IV, C-I, D-II", "A-II, B-I, C-I, D-III"),
            correctOption = 2
        ),
        Question(
            id = 178,
            text = "Which of the following are secondary lymphoid organs?\n(a) Bone marrow\n(b) Tonsils\n(c) Spleen\n(d) Thymus\nChoose the correct answer:",
            options = listOf("(a) and (d) only", "(a) and (b) only", "(b) and (c) only", "(b) and (d) only"),
            correctOption = 2
        ),
        Question(
            id = 179,
            text = "During PCR, primers bind to the DNA strands in the __________ step.",
            options = listOf("ligation", "denaturation", "extension", "annealing"),
            correctOption = 3
        ),
        Question(
            id = 180,
            text = "Statement I: Down's syndrome is caused by the absence of one of the X-chromosomes.\nStatement II: Turner's syndrome is caused by the presence of an additional copy of the chromosomes.\nChoose the correct answer:",
            options = listOf(
                "Statement I is incorrect but Statement II is correct",
                "Both Statement I and Statement II are correct",
                "Both Statement I and Statement II are incorrect",
                "Statement I is correct but Statement II is incorrect"
            ),
            correctOption = 2
        )
    )
)

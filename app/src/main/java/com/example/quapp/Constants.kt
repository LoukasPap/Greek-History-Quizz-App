package com.example.quapp

import kotlin.random.Random

object Constants {

    fun getQuestions(): ArrayList<Question> {
        var questionList = ArrayList<Question>()

        val q00 = Question(0, "Το ξεκίνημα της επανάστασης, έγινε στις 26 Μαρτίου.", false, points = 100)
        val q01 = Question(1, "Η ελληνική επανάσταση εναντίον της Οθωμανικής αυτοκρατορίας, άρχισε το 1821;", true, points = 100)
        val q02 = Question(2, "Ο Θεόδωρος Κολοκοτρώνης έμεινε γνωστός και ως 'Νέγρος του Μωριά'.", false, points = 100)
        val q03 = Question(3, "Ο Διονύσιος Σολωμός καταγόταν από την Ζάκυνθο.", true, points = 100)
        val q04 = Question(4, "Το Πρωτόκολλο του Λονδίνου, η πρώτη επίσημη, διεθνής " +
                                "διπλωματική πράξη που αναγνώριζε την Ελλάδα ως κυρίαρχο και " +
                                "ανεξάρτητο κράτος, υπεγράφη το 1829.", false, points = 100)
        val q05 = Question(5, "Η Αθήνα έγινε πρωτεύουσα του Ελληνικού κράτους το 1848.", false, points = 100)
        val q06 = Question(6, "Η απελευθέρωση της Αθήνας από τους Γερμανούς έγινε στις 14 Οκτωβρίου 1944.", true, points = 100)
        val q07 = Question(7, "Ο Γιάννης Ρίτσος έχει τιμηθεί με το βραβείο Νόμπελ Λογοτεχνίας.", false, points = 100)
        val q08 = Question(8, "Οι αρχαιολογικοί χώροι Σέσκλο και Διμήνι βρίσκονται στην Κρήτη.", false, points = 100)
        val q09 = Question(9, "Όλες οι εξουσίες, σύμφωνα με το Ελληνικό Σύνταγμα, πηγάζουν από το Σύνταγμα.", false, points = 100)
        val q10 = Question(10, "Το εκλογικό σύστημα της Ελλάδας είναι αναλογικό.", true, points = 100)
        val q11 = Question(11, "Για να εκλεγεί κάποιος βουλευτής, πρέπει να έχει συμπληρώσει την ηλικία των 18.", false, points = 100)
        val q12 = Question(12, "Η νομοθετική λειτουργία ασκείται από την Βουλή και την Κυβέρνηση.", false, points = 100)
        val q13 = Question(13, "Τα δικαστήρια, ανάλογα με το είδος των διαφορών που καλούνται να επιλύσουν, " +
                                        "διακρίνονται σε πολιτικά, ποινικά και διοικητικά.", true, points = 100)
        val q14 = Question(14, "Η Ελλάδα είναι ιδρυτικό μέλος του Οργανισμού Ηνωμένων Εθνών.", true, points = 100)
        val q15 = Question(15, "Η λειτουργία του ΟΟΣΑ, στον οποίο συμμετέχει η Ελλάδα, αποσκοπεί στην οικονομική ανάπτυξη.", true, points = 100)
        val q16 = Question(16, "Η μεταφορά της πρωτεύουσας του Ανατολικού Ρωμαϊκού Κράτους στην Κωνσταντινούπολη " +
                                    "από τον Αυτοκράτορα Κωνσταντίνο, έγινε επίσημα το 330 μ.Χ.", true, points = 100)
        val q17 = Question(17, "Τον Ύμνο για τους Ολυμπιακούς Αγώνες της Αθήνας το 1896, τον έγραψε ο Κωστής Παλαμάς.", true, points = 100)
        val q18 = Question(18, "H Ελλάδα χωρίζεται σε 332 δήμους.", true, points = 100)
        val q19 = Question(19, "Η βιομηχανία ανήκει στον τριτογενή τομέα της οικονομίας.", false, points = 100)
        val q20 = Question(20, "Ο βασιλιάς Αλέξανδρος πέθανε από το δάγκωμα μιας μαϊμούς.", true, points = 100)
        val q21 = Question(21, "Ο Ιωάννης Καποδίστριας δολοφονήθηκε έξω από την εκκλησία της Ευαγγελίστριας το 1831.", false, points = 100)
        val q22 = Question(22, "Ο Χαρίλαος Τρικούπης είπε την περίφημη φράση «δυστυχώς επτωχεύσαμεν».", true, points = 100)
        val q23 = Question(23, "Ο Γεώργιος Καραϊσκάκης ορκίστηκε στο μοναστήρι Σίμωνος Πέτρας στο Άγιο όρος.", false, points = 100)
        val q24 = Question(24, "Τα πρώτα χαρτονομίσματα στην Ελλάδα κόπηκαν το 1833.", false, points = 100)
        val q25 = Question(25, "Η Συνθήκη των Σεβρών, που αποτέλεσε μεγάλη διπλωματική επιτυχία της Ελλάδας, υπογράφηκε στις 10 Αυγούστου του 1920.", true, points = 100)
        val q26 = Question(26, "Το πρώτο επίσημο σύνταγμα της Ελλάδας ψηφίστηκε το 1844.", true, points = 100)


        questionList.add(q00)
        questionList.add(q01)
        questionList.add(q02)
        questionList.add(q03)
        questionList.add(q04)
        questionList.add(q05)
        questionList.add(q06)
        questionList.add(q07)
        questionList.add(q08)
        questionList.add(q09)
        questionList.add(q10)
        questionList.add(q11)
        questionList.add(q12)
        questionList.add(q13)
        questionList.add(q14)
        questionList.add(q15)
        questionList.add(q16)
        questionList.add(q17)
        questionList.add(q18)
        questionList.add(q19)
        questionList.add(q20)
        questionList.add(q21)
        questionList.add(q22)
        questionList.add(q23)
        questionList.add(q24)
        questionList.add(q25)
        questionList.add(q26)


        return randomQuestionsGenerator(questionList)
    }

    private fun randomQuestionsGenerator(ql: ArrayList<Question>): ArrayList<Question> {
        var randomQuestions = ArrayList<Question>()

        var randomIndexes = mutableListOf<Int>()
        while (randomIndexes.size < 8) {
            var ri = Random.nextInt(0, ql.size-1)
            if (ri !in randomIndexes) {
                randomIndexes.add(ri)
            }
        }

        randomIndexes.forEach() {
            randomQuestions.add( ql[it] )
        }
        return randomQuestions
    }
}
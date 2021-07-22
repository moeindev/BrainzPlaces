package ir.moeindeveloper.brainzplaces.mocks

import ir.moeindeveloper.brainzplaces.network.entity.DummyEntity

object MockResponses {

    /*
    [{"id":1,"first_name":"Fayina","last_name":"Peskin","email":"fpeskin0@indiegogo.com","gender":"Non-binary","ip_address":"201.149.46.0"},
    {"id":2,"first_name":"Barrie","last_name":"Brewster","email":"bbrewster1@xinhuanet.com","gender":"Agender","ip_address":"25.149.95.183"},
    {"id":3,"first_name":"Garald","last_name":"Houston","email":"ghouston2@home.pl","gender":"Bigender","ip_address":"202.161.85.225"},
    {"id":4,"first_name":"Royal","last_name":"Jeggo","email":"rjeggo3@amazon.de","gender":"Male","ip_address":"89.103.231.194"},
    {"id":5,"first_name":"Sanford","last_name":"Peacher","email":"speacher4@tamu.edu","gender":"Polygender","ip_address":"142.61.199.130"}]
     */
    val dummyResponse: ArrayList<DummyEntity> get() =
        arrayListOf(
            DummyEntity(id = 1, firstName = "Fayina", lastName = "Peskin", email = "fpeskin0@indiegogo.com", gender = "Non-binary", ipAddress = "201.149.46.0"),
            DummyEntity(id = 2, firstName = "Barrie", lastName = "Brewster", email = "bbrewster1@xinhuanet.com", gender = "Agender", ipAddress = "25.149.95.183"),
            DummyEntity(id = 3, firstName = "Garald", lastName = "Houston", email = "ghouston2@home.pl", gender = "Bigender", ipAddress = "202.161.85.225"),
            DummyEntity(id = 4, firstName = "Royal", lastName = "Jeggo", email = "rjeggo3@amazon.de", gender = "Male", ipAddress = "89.103.231.194"),
            DummyEntity(id = 5, firstName = "Sanford", lastName = "Peacher", email = "speacher4@tamu.edu", gender = "Polygender", ipAddress = "142.61.199.130"),
        )
}
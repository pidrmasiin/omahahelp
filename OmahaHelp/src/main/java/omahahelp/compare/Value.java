/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omahahelp.compare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import omahahelp.cards.Card;
import omahahelp.cards.Card.Suit;
import static omahahelp.cards.Card.Suit.CLUBS;
import static omahahelp.cards.Card.Suit.DIAMONDS;
import static omahahelp.cards.Card.Suit.HEARTS;
import static omahahelp.cards.Card.Suit.SPADES;
import omahahelp.cards.Deck;
import omahahelp.deal.Draw;

/**
 * Luokan avulla muodostetaan viidenkortin pakoille arvot.
 *
 * @author petteri
 */
public class Value implements Comparator<Card> {

    private HandsValue value;
    private Deck hand;
    private Boolean smallStraight;

    /**
     * Luodaan Value.
     */
    public Value() {
        this.hand = new Deck();
        this.value = new HandsValue(0, 0);
        this.smallStraight = false;
    }

    /**
     * Luodaan 5-kortin pakka, jonka arvoa luokassa määritellään.
     *
     * @param a pakan 1. kortti
     * @param b pakan 2. kortti
     * @param c pakan 3. kortti
     * @param d pakan 4. kortti
     * @param e pakan 5. kortti
     */
    public void setCardsToHand(Card a, Card b, Card c, Card d, Card e) {
        this.hand.getCards().clear();
        this.hand.getCards().add(a);
        this.hand.getCards().add(b);
        this.hand.getCards().add(c);
        this.hand.getCards().add(d);
        this.hand.getCards().add(e);
    }

    /**
     * Asetetaan 5 kortin pakka, jonka arvoa lasketaan.
     *
     * @param deck 5-kortin pakka.
     */
    public void setFiveCardsDeckToHand(Deck deck) {
        Deck here = deck;
        this.hand.getCards().clear();
        this.hand.getCards().add(here.getCard(0));
        this.hand.getCards().add(here.getCard(1));
        this.hand.getCards().add(here.getCard(2));
        this.hand.getCards().add(here.getCard(3));
        this.hand.getCards().add(here.getCard(4));
    }

//    /**
//     * Arvotaan kortit 5-kortin pakkaan.
//     */
//    public void drawCardsToHand() {
//        this.hand.getCards().clear();
//        Deck cards = new Deck();
//        cards.addCards();
//        Draw draw = new Draw(cards);
//        this.hand.getCards().addAll(draw.drawFlop());
//        this.hand.getCards().add(draw.drawCard());
//        this.hand.getCards().add(draw.drawCard());
//    }
    public Deck getDeck() {
        return this.hand;
    }

    /**
     * Katsotaan muodostaako pakka värin.
     *
     * @return true, jos muodostaa.
     */
    public boolean checkFlush() {

        Suit suit = this.hand.getCard(0).getSuit();
        int id = 0;
        for (int idx = 0; idx < this.hand.size(); idx++) {
            suit = this.hand.getCard(idx).getSuit();
            if (suit != this.hand.getCard(0).getSuit()) {
                id = 1;
            }
        }
        if (id == 0) {
            return true;
        }

        return false;
    }

    /**
     * Pakan koko.
     *
     * @return palauttaa pakan koon.
     */
    public int getHandSum() {
        return this.hand.size();
    }

    /**
     * Katsotaan muodostaako pakka värisuoran.
     *
     * @return true, jos muodostaa.
     */
    public boolean checkStarightFlush() {
        if (this.checkFlush() && this.checkStraight()) {
            return true;
        }
        return false;
    }

    /**
     * Katsotaan muodostaako pakka suoran. Koska ässä tuottaa ongelmia,
     * tarkastetaan onko ässää ja tarkastetaan suora sen mukaan.
     *
     * @return true, jos muodostaa.
     */
    public boolean checkStraight() {
        this.organizeHand();
        if (hand.getCard(0).getNumber() == 2 && hand.getCard(4).getNumber() == 14) {
            this.smallStraight = true;
            return this.highStraigth(this.hand.size() - 1, 1);
        }
        return this.highStraigth(this.hand.size(), 0);
    }

    /**
     * Järjestetään pakka pienemmästä suurimpaan ja tarkasteaan suoraa
     * alkupäästä.
     *
     * @param y pakan koko, jos ässä vähennetään koosta 1.
     * @param id 0, jos ässä lisätään yksi, jotta päästään leikkuriin.
     * @return true, jos haluttu määrä kortteja muodostaa suoran.
     */
    public boolean highStraigth(int y, int id) {

        this.organizeHand();
        int number = 0;
        for (int x = 0; x < y; x++) {
            number = this.hand.getCard(x).getNumber();
            number++;
            if (x < 4 && number == this.hand.getCard(x + 1).getNumber()) {
                id++;
            }
            if (id == 4) {
                return true;
            }
        }
        return false;
    }

    /**
     * Apumetodi, jossa pakka järjestetään. Luultavasti tyhmä metodi.
     *
     * @return palauttaa järjestetyn pakan.
     */
    public ArrayList<Integer> helpToCheckSames() {
        this.organizeHand();
        ArrayList help = new ArrayList<>();
        for (int idx = 0; idx < this.hand.size(); idx++) {
            help.add(this.hand.getCard(idx).getNumber());
        }
        return help;
    }

    /**
     * Luodaan lista lista, jossa kortit on järjestetty siten, että listan
     * ensimäisenä on sellaisen kortin numero, jota arvioitava pakka sisältää
     * eniten. Jos siis arvioitavassa pakassa on esimerkiksi kortteja, joiden
     * numeroarvo on 2, 2, 3, 4, 3, metodi luo listan, jossa jossa ensimmäisenä
     * on HandsValue (2, 3), jossa 2 kuvaa, sitä montako korttia ja 3, mitä
     * numeroa. Tulostus olisi siis muotoa. 2x3, 2x2, 1x4
     *
     * @return ArrayListin, joka kuvaa montako mitäkin korttia on suuruus
     * järjestyksessä.
     */
    public ArrayList<HandsValue> checkSames() {
        int sames = 0;
        this.organizeHand();
        ArrayList help = new ArrayList<>();
        help = this.helpToCheckSames();
        ArrayList out = new ArrayList<HandsValue>();
        int y = 4;
        for (int x = 0; x < 5; x++) {
            int check = (int) help.get(y);
            for (int idx = 0; idx < help.size(); idx++) {
                if ((int) help.get(idx) == check) {
                    sames++;
                }
            }
            HandsValue hand = new HandsValue(sames, check);
            if (out.isEmpty()) {
                out.add(hand);
            }
            if (!out.get(out.size() - 1).toString().equals(hand.toString())) {
                out.add(hand);
            }
            sames = 0;
            y--;

        }
        Collections.sort(out, (HandsValue o1, HandsValue o2) -> o2.getType() - o1.getType());
        return out;
    }

    /**
     * Katsotaan onko neloset.
     *
     * @return true, jos neloset.
     */
    public boolean checkFourofKind() {
        return this.checkSames().get(0).getType() == 4;
    }

    /**
     * Katsotaan onko kolmoset.
     *
     * @return true, jos kolmoset.
     */
    public boolean checkThreeOfKind() {
        if (this.checkSames().get(0).getType() == 3 && this.checkSames().get(1).getType() != 2) {
            return true;
        }
        return false;
    }

    /**
     * Katsotaan onko täyskäsi.
     *
     * @return true, jos täyskäsi.
     */
    public boolean checkFullHouse() {
        if (this.checkSames().get(0).getType() == 3 && this.checkSames().get(1).getType() == 2) {
            return true;
        }
        return false;
    }

    /**
     * Katsotaan onko kaksiparia.
     *
     * @return true, jos kaksiparia.
     */
    public boolean checkTwoPairs() {
        if (this.checkSames().get(0).getType() == 2 && this.checkSames().get(1).getType() == 2) {
            return true;
        }
        return false;
    }

    /**
     * Katsotaan, onko pari.
     *
     * @return true, jos pari.
     */
    public boolean checkPair() {
        if (this.checkSames().get(0).getType() == 2 && 2 != this.checkSames().get(1).getType()) {
            return true;
        }
        return false;
    }

    /**
     * Järjestetään käsi.
     */
    public void organizeHand() {
        Collections.sort(this.hand.getCards(), this);
    }

    /**
     * Palautaa käden arvon Int-muodossa, mitä isompi parempi.
     *
     * @return käden arvo.
     */
    public HandsValue getHandsValue() {
        return this.value;
    }

    /**
     * Asetetaan kädelle arvo sen tyypin mukaan. Mitä isompi parempi.
     *
     * @return palauttaa käden tyypin Int:nä
     */
    public int getType() {
        if (this.checkStarightFlush()) {
            return 900000000;
        }
        if (this.checkFourofKind()) {
            return 800000000;
        }
        if (this.checkFullHouse()) {
            return 700000000;
        }
        if (this.checkFlush()) {
            return 600000000;
        }
        if (this.checkStraight()) {
            return 500000000;
        }
        if (this.checkThreeOfKind()) {
            return 400000000;
        }
        if (this.checkTwoPairs()) {
            return 300000000;
        }
        if (this.checkPair()) {
            return 200000000;
        }
        return 0;

    }

    /**
     * Luodaan lista, jonka ensimmäisenä on pakan merkitsevin kortti.
     *
     * @return palauttaa ArrayListin, jossa pakka merkitsevyys järjestyksessä.
     */
    public ArrayList<Integer> makeOrganizedArray() {
        ArrayList<Integer> out = new ArrayList<>();
        for (int x = 0; x < this.checkSames().size(); x++) {
            for (int y = 0; y < this.checkSames().get(x).getType(); y++) {

                out.add(this.checkSames().get(x).getValue());
            }
        }
        return out;
    }

    /**
     * Asettaa kädelle arvon numeroiden mukaan. Ensin pakka järjestetään siten,
     * että merkitsevin kortti on ensimmäisenä. Sitten merkitsevyyden mukaan
     * lisätään arvoa.
     */
    public void setValue() {
        ArrayList<Integer> out = this.makeOrganizedArray();
        int x = 0;
        Card a = new Card(15, CLUBS);
        if (this.smallStraight) {
            a = this.hand.deckContainsAce();
            hand.eraseByString(a.toString());
            x++;
        }
        while (x < out.size()) {

            if (x == 0) {
                this.value.addValueToValue(out.get(x) * 10000000);

            }
            if (x == 1) {
                this.value.addValueToValue(out.get(x) * 100000);

            }
            if (x == 2) {
                this.value.addValueToValue(out.get(x) * 1000);

            }
            if (x == 3) {
                this.value.addValueToValue(out.get(x) * 10);

            }
            if (x == 4) {
                this.value.addValueToValue(out.get(x));

            }
            x++;
        }
        if (this.smallStraight) {
            hand.addOneCard(a);
        }

    }

    /**
     * Luodaan kädelle arvo ja palautetaan se. Mitä isompi, sen parempi.
     *
     * @return käden arvo int:nä.
     */
    public int getValue() {
        this.value.erase();
        int x = this.getType();
        this.value.addValueToValue(x);
        this.setValue();
        return this.value.getHandValue();
    }

    @Override
    public int compare(Card o1, Card o2) {
        return o1.getNumber() - o2.getNumber();
    }

}

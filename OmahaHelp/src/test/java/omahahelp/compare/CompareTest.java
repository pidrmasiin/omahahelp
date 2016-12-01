/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omahahelp.compare;

import omahahelp.cards.Card;
import static omahahelp.cards.Card.Suit.CLUBS;
import static omahahelp.cards.Card.Suit.HEARTS;
import static omahahelp.cards.Card.Suit.SPADES;
import omahahelp.cards.Deck;
import omahahelp.cards.PlayersCards;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author petteri
 */
public class CompareTest {

    public CompareTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void getCardsTest() {
        Deck cards = new Deck();
        cards.addCards();
        Card cardA = new Card(13, SPADES);
        Card cardB = new Card(3, SPADES);
        Card cardC = new Card(2, SPADES);
        Card cardD = new Card(14, SPADES);
        PlayersCards handA = new PlayersCards(cards, cardA, cardB);
        PlayersCards handB = new PlayersCards(cards, cardC, cardD);
        Compare compare = new Compare(handA, handB, cards);
        assertEquals(compare.getCards(), cards);
    }

    @Test
    public void addFlopsToMapTest() {
        Deck cards = new Deck();
        cards.addCards();
        Card cardA = new Card(13, CLUBS);
        Card cardB = new Card(3, SPADES);
        Card cardC = new Card(2, HEARTS);
        Card cardD = new Card(14, SPADES);
        PlayersCards handA = new PlayersCards(cards, cardA, cardB);
        PlayersCards handB = new PlayersCards(cards, cardC, cardD);
        Compare compare = new Compare(handA, handB, cards);
        compare.addWinsAndCardsToHashMaps(true, false);
        assertEquals(compare.getMap().size(), 17296);
    }
    
    @Test
    public void calculateTurnTest(){
        Deck cards = new Deck();
        cards.addCards();
        Card cardA = new Card(13, CLUBS);
        Card cardB = new Card(3, SPADES);
        Card cardC = new Card(2, HEARTS);
        Card cardD = new Card(14, SPADES);
        PlayersCards handA = new PlayersCards(cards, cardA, cardB);
        PlayersCards handB = new PlayersCards(cards, cardC, cardD);
        Compare compare = new Compare(handA, handB, cards);
        compare.addWinsAndCardsToHashMaps(false, true);
        assertEquals(compare.getTurns().size(), 194580);
    }
    
    @Test
    public void setWinsAndTieTest(){
        Deck cards = new Deck();
        cards.addCards();
        Card cardA = new Card(13, CLUBS);
        Card cardB = new Card(3, SPADES);
        Card cardC = new Card(2, HEARTS);
        Card cardD = new Card(14, SPADES);
        PlayersCards handA = new PlayersCards(cards, cardA, cardB);
        PlayersCards handB = new PlayersCards(cards, cardC, cardD);
        Compare compare = new Compare(handA, handB, cards);
        compare.setWinsAndTie(1, 2);
        assertEquals(compare.getAwins(), 0);
        assertEquals(compare.getTies(), 0);
        assertEquals(compare.getbWins(), 1);
        compare.setWinsAndTie(1, 1);
        assertEquals(compare.getTies(), 1);
        compare.setWinsAndTie(2, 1);
        assertEquals(compare.getAwins(), 1);
        
    }
    
    @Test
    public void helpCompareTest(){
        Deck cards = new Deck();
        cards.addCards();
        Card cardA = new Card(13, CLUBS);
        Card cardB = new Card(3, SPADES);
        Card cardC = new Card(2, HEARTS);
        Card cardD = new Card(14, SPADES);
        PlayersCards handA = new PlayersCards(cards, cardA, cardB);
        PlayersCards handB = new PlayersCards(cards, cardC, cardD);
        Compare compare = new Compare(handA, handB, cards);
        int a = compare.helpCompare(1, 2);
        
        assertEquals(a, 2);
        a = compare.helpCompare(a, 8);
        assertEquals(a, 8);
        a = compare.helpCompare(7, a);
        assertEquals(a, 8);
        assertEquals(a, 8);
        a = compare.helpCompare(8, a);
        assertEquals(a, 8);
    }

}
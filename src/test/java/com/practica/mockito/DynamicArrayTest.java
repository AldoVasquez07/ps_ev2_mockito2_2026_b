package com.practica.mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

@ExtendWith(MockitoExtension.class)
public class DynamicArrayTest {

    private DynamicArray<ElementoPrueba> array;

    interface ElementoPrueba {
        void procesar();
    }

    @BeforeEach
    void setUp() {
        array = new DynamicArray<>(2);
    }

    @Test
    void testAddAndSize() {
        array.add(mock(ElementoPrueba.class));
        array.add(mock(ElementoPrueba.class));
        array.add(mock(ElementoPrueba.class));

        assertEquals(3, array.getSize());
    }

    @Test
    void testGetAndPut() {
        ElementoPrueba mockElement = mock(ElementoPrueba.class);
        array.add(mock(ElementoPrueba.class));
        array.put(0, mockElement);

        assertEquals(mockElement, array.get(0));
    }

    @Test
    void testRemove() {
        ElementoPrueba e1 = mock(ElementoPrueba.class);
        ElementoPrueba e2 = mock(ElementoPrueba.class);
        array.add(e1);
        array.add(e2);

        ElementoPrueba removed = array.remove(0);

        assertEquals(e1, removed);
        assertEquals(1, array.getSize());
        assertEquals(e2, array.get(0));
    }

    @Test
    void testIteratorHasNextAndNext() {
        ElementoPrueba e1 = mock(ElementoPrueba.class);
        array.add(e1);

        Iterator<ElementoPrueba> it = array.iterator();

        assertTrue(it.hasNext());
        assertEquals(e1, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    void testIteratorNoSuchElementException() {
        Iterator<ElementoPrueba> it = array.iterator();
        assertThrows(NoSuchElementException.class, it::next);
    }

    @Test
    void testForEachRemaining(@Mock Consumer<ElementoPrueba> mockAction) {
        ElementoPrueba e1 = mock(ElementoPrueba.class);
        ElementoPrueba e2 = mock(ElementoPrueba.class);
        array.add(e1);
        array.add(e2);

        Iterator<ElementoPrueba> it = array.iterator();
        it.forEachRemaining(mockAction);

        verify(mockAction, times(1)).accept(e1);
        verify(mockAction, times(1)).accept(e2);
    }

    @Test
    void testToString() {
        DynamicArray<String> stringArray = new DynamicArray<>();
        stringArray.add("Hola");
        stringArray.add("Mundo");

        assertEquals("[Hola, Mundo]", stringArray.toString());
    }
}
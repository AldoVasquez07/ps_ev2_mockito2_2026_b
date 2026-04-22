package com.practica.mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CircleLinkedListTest {

    private CircleLinkedList<TestElement> list;

    interface TestElement {
        String getName();
    }

    @BeforeEach
    void setUp() {
        list = new CircleLinkedList<>();
    }

    @Test
    void testAppendIncreasesSize() {
        list.append(mock(TestElement.class));
        assertEquals(1, list.getSize());
    }

    @Test
    void testAppendNullThrowsException() {
        assertThrows(NullPointerException.class, () -> {
            list.append(null);
        });
    }

    @Test
    void testRemoveInvalidIndexThrowsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.remove(1);
        });
    }

    @Test
    void testRemoveReturnsCorrectValue(@Mock TestElement mockElement) {
        when(mockElement.getName()).thenReturn("Mocked Item");
        
        list.append(mockElement);

        TestElement removed = list.remove(0);

        assertEquals(mockElement, removed);
        assertEquals("Mocked Item", removed.getName());
        assertEquals(0, list.getSize());
        
        verify(mockElement).getName(); 
    }

    @Test
    void testCircularToStringFormat() {
        CircleLinkedList<String> stringList = new CircleLinkedList<>();
        stringList.append("Nodo1");
        stringList.append("Nodo2");
        
        String result = stringList.toString();
        
        assertTrue(result.contains("Nodo1"));
        assertTrue(result.contains("Nodo2"));
        assertEquals("[ Nodo1 , Nodo2 ]", result);
    }
}
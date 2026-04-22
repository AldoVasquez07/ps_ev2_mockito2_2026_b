package com.practica.mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

@ExtendWith(MockitoExtension.class)
public class CircleLinkedListTest {

    @Mock
    private TestElement mockElement;

    @Spy
    private CircleLinkedList<TestElement> spyList;

    @Captor
    private ArgumentCaptor<TestElement> elementCaptor;

    interface TestElement {
        String getName();
    }

    @Test
    void testStep4_MockCreation() {
        assertNotNull(mockElement);
        spyList.append(mockElement);
        assertEquals(1, spyList.getSize());
    }

    @Test
    void testStep5_2_Verification() {
        spyList.append(mockElement);
        verify(spyList).append(mockElement);
    }

    @Test
    void testStep5_3_Exceptions() {
        assertThrows(NullPointerException.class, () -> spyList.append(null));
        assertThrows(IndexOutOfBoundsException.class, () -> spyList.remove(99));
    }

    @Test
    void testStep6_1_WhenThenReturn() {
        when(mockElement.getName()).thenReturn("Advanced Mock");
        spyList.append(mockElement);
        assertEquals("Advanced Mock", spyList.remove(0).getName());
    }

    @Test
    void testStep6_2_DoThrowWhen() {
        doThrow(new RuntimeException()).when(spyList).getSize();
        assertThrows(RuntimeException.class, () -> spyList.getSize());
    }

    @Test
    void testStep7_Spy() {
        CircleLinkedList<String> realList = new CircleLinkedList<>();
        CircleLinkedList<String> spyReal = spy(realList);
        
        spyReal.append("Data");
        verify(spyReal).append("Data");
        assertEquals(1, spyReal.getSize());
    }

    @Test
    void testStep9_ArgumentCaptor() {
        spyList.append(mockElement);
        verify(spyList).append(elementCaptor.capture());
        assertEquals(mockElement, elementCaptor.getValue());
    }

    @Test
    void testStep10_Answers() {
        when(mockElement.getName()).thenAnswer(new Answer<String>() {
            private int count = 0;
            @Override
            public String answer(InvocationOnMock invocation) {
                return "ElementCalled:" + (++count);
            }
        });

        spyList.append(mockElement);
        TestElement e = spyList.remove(0);
        
        assertEquals("ElementCalled:1", e.getName());
        assertEquals("ElementCalled:2", e.getName());
    }
}
package ru.apertum.qsystem.custom.testapi.common.models;

import javax.swing.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Список услуг, обрабатываемых пользователем. Класс - рулит списком услуг юзера. Должен строиться для каждого юзера и он же должен отображаться в админской
 * проге. Элементы списка это QPlanService. Пока пустой. Нужен для того чтобы при необходимости чот-то переопределить.
 *
 * @author Evgeniy Egorov
 */
public class QPlanServiceList extends AbstractListModel implements List {

    private final List<QPlanService> services;

    public List<QPlanService> getServices() {
        return services;
    }

    public QPlanServiceList(List<QPlanService> services) {
        this.services = services;
    }

    @Override
    public int getSize() {
        return services.size();
    }

    @Override
    public Object getElementAt(int index) {
        return services.get(index);
    }

    /**
     * Удалим элементик.
     *
     * @param obj этот.
     * @return все норм.
     */
    public boolean removeElement(QPlanService obj) {
        final int index = services.indexOf(obj);
        final boolean res = services.remove(obj);
        fireIntervalRemoved(this, index, index);
        return res;
    }

    /**
     * Добавляем элементик.
     *
     * @param obj этот.
     */
    public void addElement(QPlanService obj) {
        final int index = services.size();
        services.add(obj);
        fireIntervalAdded(this, index, index);
    }

    @Override
    public int size() {
        return getSize();
    }

    @Override
    public boolean isEmpty() {
        return getSize() == 0;
    }

    @Override
    public boolean contains(Object o) {
        return services.contains(o);
    }

    @Override
    public Iterator iterator() {
        return services.iterator();
    }

    @Override
    public QPlanService[] toArray() {
        return services.toArray(new QPlanService[services.size()]);
    }

    @Override
    public QPlanService[] toArray(Object[] a) {
        return (QPlanService[]) services.toArray(a);
    }

    @Override
    public QPlanService get(int index) {
        return services.get(index);
    }

    @Override
    public boolean add(Object e) {
        return services.add((QPlanService) e);
    }

    @Override
    public void add(int index, Object element) {
        services.add(index, (QPlanService) element);
    }

    @Override
    public boolean remove(Object o) {
        return services.remove((QPlanService) o);
    }

    @Override
    public QPlanService remove(int index) {
        return services.remove(index);
    }

    @Override
    public boolean containsAll(Collection c) {
        return services.containsAll(c);
    }

    @Override
    public boolean addAll(Collection c) {
        return services.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection c) {
        return services.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection c) {
        return services.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection c) {
        return services.retainAll(c);
    }

    @Override
    public void clear() {
        services.clear();
    }


    @Override
    public QPlanService set(int index, Object element) {
        return services.set(index, (QPlanService) element);
    }

    @Override
    public int indexOf(Object o) {
        return services.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return services.lastIndexOf(o);
    }

    @Override
    public ListIterator listIterator() {
        return services.listIterator();
    }

    @Override
    public ListIterator listIterator(int index) {
        return services.listIterator(index);
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        return services.subList(fromIndex, toIndex);
    }
}


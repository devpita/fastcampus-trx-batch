/*
 * This file is generated by jOOQ.
 */
package com.pitachips.trxbatch.generated.tables;


import com.pitachips.trxbatch.generated.Keys;
import com.pitachips.trxbatch.generated.Trxbatch;
import com.pitachips.trxbatch.generated.tables.records.TrxRecord;

import java.time.LocalDateTime;
import java.util.Collection;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.SQL;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.Stringly;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Trx extends TableImpl<TrxRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>trxbatch.TRX</code>
     */
    public static final Trx TRX = new Trx();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TrxRecord> getRecordType() {
        return TrxRecord.class;
    }

    /**
     * The column <code>trxbatch.TRX.id</code>.
     */
    public final TableField<TrxRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>trxbatch.TRX.ticker_code</code>.
     */
    public final TableField<TrxRecord, String> TICKER_CODE = createField(DSL.name("ticker_code"), SQLDataType.VARCHAR(40).nullable(false), this, "");

    /**
     * The column <code>trxbatch.TRX.ticker_name_kr</code>.
     */
    public final TableField<TrxRecord, String> TICKER_NAME_KR = createField(DSL.name("ticker_name_kr"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>trxbatch.TRX.acct_no</code>.
     */
    public final TableField<TrxRecord, String> ACCT_NO = createField(DSL.name("acct_no"), SQLDataType.VARCHAR(10).nullable(false), this, "");

    /**
     * The column <code>trxbatch.TRX.price</code>.
     */
    public final TableField<TrxRecord, Long> PRICE = createField(DSL.name("price"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>trxbatch.TRX.quantity</code>.
     */
    public final TableField<TrxRecord, Long> QUANTITY = createField(DSL.name("quantity"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>trxbatch.TRX.currency</code>.
     */
    public final TableField<TrxRecord, String> CURRENCY = createField(DSL.name("currency"), SQLDataType.VARCHAR(3).nullable(false), this, "");

    /**
     * The column <code>trxbatch.TRX.type</code>.
     */
    public final TableField<TrxRecord, String> TYPE = createField(DSL.name("type"), SQLDataType.VARCHAR(20).nullable(false), this, "");

    /**
     * The column <code>trxbatch.TRX.transactioned_at</code>.
     */
    public final TableField<TrxRecord, LocalDateTime> TRANSACTIONED_AT = createField(DSL.name("transactioned_at"), SQLDataType.LOCALDATETIME(0).nullable(false), this, "");

    /**
     * The column <code>trxbatch.TRX.created_at</code>.
     */
    public final TableField<TrxRecord, LocalDateTime> CREATED_AT = createField(DSL.name("created_at"), SQLDataType.LOCALDATETIME(0).nullable(false).defaultValue(DSL.field(DSL.raw("CURRENT_TIMESTAMP"), SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>trxbatch.TRX.updated_at</code>.
     */
    public final TableField<TrxRecord, LocalDateTime> UPDATED_AT = createField(DSL.name("updated_at"), SQLDataType.LOCALDATETIME(0).nullable(false).defaultValue(DSL.field(DSL.raw("CURRENT_TIMESTAMP"), SQLDataType.LOCALDATETIME)), this, "");

    private Trx(Name alias, Table<TrxRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private Trx(Name alias, Table<TrxRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>trxbatch.TRX</code> table reference
     */
    public Trx(String alias) {
        this(DSL.name(alias), TRX);
    }

    /**
     * Create an aliased <code>trxbatch.TRX</code> table reference
     */
    public Trx(Name alias) {
        this(alias, TRX);
    }

    /**
     * Create a <code>trxbatch.TRX</code> table reference
     */
    public Trx() {
        this(DSL.name("TRX"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Trxbatch.TRXBATCH;
    }

    @Override
    public Identity<TrxRecord, Long> getIdentity() {
        return (Identity<TrxRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<TrxRecord> getPrimaryKey() {
        return Keys.KEY_TRX_PRIMARY;
    }

    @Override
    public Trx as(String alias) {
        return new Trx(DSL.name(alias), this);
    }

    @Override
    public Trx as(Name alias) {
        return new Trx(alias, this);
    }

    @Override
    public Trx as(Table<?> alias) {
        return new Trx(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Trx rename(String name) {
        return new Trx(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Trx rename(Name name) {
        return new Trx(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Trx rename(Table<?> name) {
        return new Trx(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Trx where(Condition condition) {
        return new Trx(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Trx where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Trx where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Trx where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Trx where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Trx where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Trx where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Trx where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Trx whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Trx whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}

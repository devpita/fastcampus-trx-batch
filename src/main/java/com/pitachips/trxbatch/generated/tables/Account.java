/*
 * This file is generated by jOOQ.
 */
package com.pitachips.trxbatch.generated.tables;


import com.pitachips.trxbatch.generated.Keys;
import com.pitachips.trxbatch.generated.Trxbatch;
import com.pitachips.trxbatch.generated.tables.records.AccountRecord;

import java.time.LocalDateTime;
import java.util.Collection;

import org.jooq.Condition;
import org.jooq.Field;
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
public class Account extends TableImpl<AccountRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>trxbatch.ACCOUNT</code>
     */
    public static final Account ACCOUNT = new Account();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AccountRecord> getRecordType() {
        return AccountRecord.class;
    }

    /**
     * The column <code>trxbatch.ACCOUNT.acct_no</code>.
     */
    public final TableField<AccountRecord, String> ACCT_NO = createField(DSL.name("acct_no"), SQLDataType.VARCHAR(10).nullable(false), this, "");

    /**
     * The column <code>trxbatch.ACCOUNT.customer_id</code>.
     */
    public final TableField<AccountRecord, Long> CUSTOMER_ID = createField(DSL.name("customer_id"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>trxbatch.ACCOUNT.type</code>.
     */
    public final TableField<AccountRecord, String> TYPE = createField(DSL.name("type"), SQLDataType.VARCHAR(20).nullable(false), this, "");

    /**
     * The column <code>trxbatch.ACCOUNT.balance</code>.
     */
    public final TableField<AccountRecord, Long> BALANCE = createField(DSL.name("balance"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>trxbatch.ACCOUNT.status</code>.
     */
    public final TableField<AccountRecord, String> STATUS = createField(DSL.name("status"), SQLDataType.VARCHAR(20).nullable(false), this, "");

    /**
     * The column <code>trxbatch.ACCOUNT.created_at</code>.
     */
    public final TableField<AccountRecord, LocalDateTime> CREATED_AT = createField(DSL.name("created_at"), SQLDataType.LOCALDATETIME(0).nullable(false).defaultValue(DSL.field(DSL.raw("CURRENT_TIMESTAMP"), SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>trxbatch.ACCOUNT.updated_at</code>.
     */
    public final TableField<AccountRecord, LocalDateTime> UPDATED_AT = createField(DSL.name("updated_at"), SQLDataType.LOCALDATETIME(0).nullable(false).defaultValue(DSL.field(DSL.raw("CURRENT_TIMESTAMP"), SQLDataType.LOCALDATETIME)), this, "");

    private Account(Name alias, Table<AccountRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private Account(Name alias, Table<AccountRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>trxbatch.ACCOUNT</code> table reference
     */
    public Account(String alias) {
        this(DSL.name(alias), ACCOUNT);
    }

    /**
     * Create an aliased <code>trxbatch.ACCOUNT</code> table reference
     */
    public Account(Name alias) {
        this(alias, ACCOUNT);
    }

    /**
     * Create a <code>trxbatch.ACCOUNT</code> table reference
     */
    public Account() {
        this(DSL.name("ACCOUNT"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Trxbatch.TRXBATCH;
    }

    @Override
    public UniqueKey<AccountRecord> getPrimaryKey() {
        return Keys.KEY_ACCOUNT_PRIMARY;
    }

    @Override
    public Account as(String alias) {
        return new Account(DSL.name(alias), this);
    }

    @Override
    public Account as(Name alias) {
        return new Account(alias, this);
    }

    @Override
    public Account as(Table<?> alias) {
        return new Account(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Account rename(String name) {
        return new Account(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Account rename(Name name) {
        return new Account(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Account rename(Table<?> name) {
        return new Account(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Account where(Condition condition) {
        return new Account(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Account where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Account where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Account where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Account where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Account where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Account where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Account where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Account whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Account whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}